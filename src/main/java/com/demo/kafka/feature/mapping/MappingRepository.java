package com.demo.kafka.feature.mapping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MappingRepository extends JpaRepository<Mapping,Long> {
    List<Mapping> findByTopicName(String topicName);
}
