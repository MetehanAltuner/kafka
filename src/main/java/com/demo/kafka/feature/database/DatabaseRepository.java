package com.demo.kafka.feature.database;

import com.demo.kafka.feature.database.Database;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseRepository extends JpaRepository<Database,Long> {
    Database findByName(String name);
}
