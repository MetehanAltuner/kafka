package com.demo.kafka.repository;

import com.demo.kafka.entity.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MappingsRepository extends JpaRepository<Mapping,Long> {
    List<Mapping> findByTopicName(String topicName);
}
