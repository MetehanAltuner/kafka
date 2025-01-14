package com.demo.kafka.service;

import com.demo.kafka.config.DatabaseConnectionUtil;
import com.demo.kafka.feature.database.Database;
import com.demo.kafka.feature.mapping.Mapping;
import com.demo.kafka.feature.tables.Tables;
import com.demo.kafka.feature.database.DatabaseRepository;
import com.demo.kafka.feature.mapping.MappingRepository;
import com.demo.kafka.feature.tables.TablesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicKafkaConsumer {

    private final MappingRepository mappingsRepository;
    private final TablesRepository tableRepository;
    private final DatabaseRepository databaseRepository;
    private final ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;
    private final ObjectMapper objectMapper;
    private final Map<String, MessageListenerContainer> activeListeners = new HashMap<>();

    public DynamicKafkaConsumer(MappingRepository mappingsRepository,
                                TablesRepository tableRepository,
                                DatabaseRepository databaseRepository,
                                ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
        this.mappingsRepository = mappingsRepository;
        this.tableRepository = tableRepository;
        this.databaseRepository = databaseRepository;
        this.containerFactory = containerFactory;
        this.objectMapper = new ObjectMapper();
    }

    public void subscribeToTopics(List<String> topics) {
        for (String topic : topics) {
            if (!activeListeners.containsKey(topic)) {
                var container = containerFactory.createContainer(topic);
                container.getContainerProperties().setGroupId("dynamic-group");

                container.setupMessageListener((MessageListener<String, String>) record -> {
                    processMessage(topic, record);
                });

                container.start();
                activeListeners.put(topic, container);
                System.out.println("Subscribed to topic: " + topic);
            }
        }
    }

    public void processMessage(String topic, ConsumerRecord<String, String> record) {
        System.out.println("Processing Topic: " + topic + " | Message: " + record.value());

        try {
            JsonNode rootNode = objectMapper.readTree(record.value());
            JsonNode payload = rootNode.get("payload");

            if (payload == null) {
                System.err.println("Payload is null. Skipping message...");
                return;
            }

            String operation = payload.get("op").asText(); // İşlem türü (INSERT, UPDATE, DELETE)
            JsonNode after = payload.get("after");
            JsonNode before = payload.get("before");

            List<Mapping> mappings = mappingsRepository.findByTopicName(topic);

            for (Mapping mapping : mappings) {
                Tables table = mapping.getTargetColumn().getTable();
                Database database = table.getDatabase();

                if (database != null) {
                    switch (operation) {
                        case "c":
                            if (after != null) handleInsert(database, mapping, after);
                            break;
                        case "u":
                            if (after != null) handleUpdate(database, mapping, before, after);
                            break;
                        case "d":
                            if (before != null) handleDelete(database, mapping, before);
                            break;
                        default:
                            System.out.println("Unknown operation: " + operation);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInsert(Database database, Mapping mapping, JsonNode after) {
        String value = (String) extractValue(after, mapping.getSourceColumn(), String.class);
        performUpdate(database, mapping, value, null, "INSERT");
    }

    private void handleUpdate(Database database, Mapping mapping, JsonNode before, JsonNode after) {
        String newValue = (String) extractValue(after, mapping.getSourceColumn(), String.class);
        Long id = (Long) extractValue(after, "id", Long.class);
        performUpdate(database, mapping, newValue, id, "UPDATE");
    }

    private void handleDelete(Database database, Mapping mapping, JsonNode before) {
        Long id = (Long) extractValue(before, "id", Long.class);
        performDelete(database, mapping, id);
    }

    private void performUpdate(Database database, Mapping mapping, String value, Long id, String operationType) {
        EntityManager entityManager = DatabaseConnectionUtil.createEntityManager(database);

        try {
            entityManager.getTransaction().begin();

            String sql = "UPDATE " + mapping.getTargetColumn().getTable().getName() +
                    " SET " + mapping.getTargetColumn().getName() + " = :value " +
                    "WHERE id = :id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("value", value);
            query.setParameter("id", id);

            int updated = query.executeUpdate();
            System.out.println(operationType + " Operation - Updated Rows: " + updated);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void performDelete(Database database, Mapping mapping, Long id) {
        EntityManager entityManager = DatabaseConnectionUtil.createEntityManager(database);

        try {
            entityManager.getTransaction().begin();

            String sql = "DELETE FROM " + mapping.getTargetColumn().getTable().getName() + " WHERE id = :id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("id", id);

            int deleted = query.executeUpdate();
            System.out.println("DELETE Operation - Deleted Rows: " + deleted);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private Object extractValue(JsonNode node, String columnName, Class<?> targetType) {
        if (node.has(columnName)) {
            String value = node.get(columnName).asText();
            try {
                if (targetType == Integer.class) {
                    return Integer.parseInt(value);
                } else if (targetType == Long.class) {
                    return Long.parseLong(value);
                } else if (targetType == Double.class) {
                    return Double.parseDouble(value);
                } else {
                    return value;
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Cannot convert value: " + value + " to type: " + targetType.getName(), e);
            }
        }
        return null;
    }
}
