package com.demo.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "tcdd-test-topic", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Alınan Mesaj: " + message);
    }
}

