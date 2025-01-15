package com.demo.kafka.config;

import com.demo.kafka.feature.database.Database;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectionUtil {

    public static EntityManager createEntityManager(Database database) {
        Map<String, Object> properties = new HashMap<>();
        try {

            properties.put("hibernate.connection.url", database.getConnectionUrl());
            properties.put("hibernate.connection.username", database.getUsername());
            properties.put("hibernate.connection.password", database.getPassword());



            EntityManagerFactory emf = Persistence.createEntityManagerFactory("dynamic-persistence-unit", properties);

            return emf.createEntityManager();

        } catch (PersistenceException e) {

            System.err.println("Veri tabanı bağlantısı oluşturulurken bir hata oluştu: " + e.getMessage());
            throw new RuntimeException("Veri tabanı bağlantısı başarısız. Lütfen bağlantı ayarlarını kontrol edin.", e);
        } catch (Exception e) {

            System.err.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
            throw new RuntimeException("Beklenmeyen bir hata oluştu.", e);
        }
    }
}


