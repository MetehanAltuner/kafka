package com.demo.kafka.service;

import com.demo.kafka.entity.Database;
import com.demo.kafka.entity.Mapping;
import com.demo.kafka.repository.DatabaseRepository;
import com.demo.kafka.repository.MappingsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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

    private final MappingsRepository mappingsRepository;
    private final DatabaseRepository databasesRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;
    private final ObjectMapper objectMapper;
    private final Map<String, MessageListenerContainer> activeListeners = new HashMap<>();

    public DynamicKafkaConsumer(MappingsRepository mappingsRepository,
                                DatabaseRepository databasesRepository,
                                EntityManagerFactory entityManagerFactory,
                                ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
        this.mappingsRepository = mappingsRepository;
        this.databasesRepository = databasesRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.containerFactory = containerFactory;
        this.objectMapper = new ObjectMapper();
    }

    public void subscribeToTopics(List<String> topics) {
        for (String topic : topics) {
            if (!activeListeners.containsKey(topic)) {
                var container = containerFactory.createContainer(topic);

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

            String operation = payload.get("op").asText(); // İşlem türü (c, u, d)
            JsonNode after = payload.get("after");
            JsonNode before = payload.get("before");

            List<Mapping> mappings = mappingsRepository.findByTopicName(topic);

            for (Mapping mapping : mappings) {
                Database database = getDatabaseForSinkTable(mapping.getSinkTable());

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

    private Database getDatabaseForSinkTable(String sinkTable) {
        return databasesRepository.findAll().stream()
                .filter(db -> db.getConnectionUrl().contains(sinkTable))
                .findFirst()
                .orElse(null);
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
        EntityManager entityManager = createEntityManagerForDatabase(database);

        try {
            entityManager.getTransaction().begin();

            String sql = "UPDATE " + mapping.getSinkTable() +
                    " SET " + mapping.getSinkColumn() + " = :value " +
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
        EntityManager entityManager = createEntityManagerForDatabase(database);

        try {
            entityManager.getTransaction().begin();

            String sql = "DELETE FROM " + mapping.getSinkTable() + " WHERE id = :id";

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
                    return value; // Varsayılan olarak String
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Cannot convert value: " + value + " to type: " + targetType.getName(), e);
            }
        }
        return null;
    }

    private EntityManager createEntityManagerForDatabase(Database database) {
        return entityManagerFactory.createEntityManager();
    }
}
