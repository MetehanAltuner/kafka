package com.demo.kafka.feature.topic;

import com.demo.kafka.feature.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {
    Topic findByName(String name);
}
