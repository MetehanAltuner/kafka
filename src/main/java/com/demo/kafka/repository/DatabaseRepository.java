package com.demo.kafka.repository;

import com.demo.kafka.entity.Database;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseRepository extends JpaRepository<Database,Long> {
    Database findByName(String name);
}
