package com.demo.kafka.repository;

import com.demo.kafka.entity.TableDatabaseMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableDatabaseMappingRepository extends JpaRepository<TableDatabaseMapping,Long> {
    Optional<TableDatabaseMapping> findBySinkTable(String sinkTable);
}
