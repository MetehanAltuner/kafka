package com.demo.kafka.repository;

import com.demo.kafka.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {
    Topic findByName(String name);
}
