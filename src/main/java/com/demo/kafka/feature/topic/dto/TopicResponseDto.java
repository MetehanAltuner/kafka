package com.demo.kafka.feature.topic.dto;

import com.demo.kafka.feature.topic.Topic;

public class TopicResponseDto {

    private Long id;
    private String name;
    private String description;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static TopicResponseDto fromEntity(Topic topic) {
        TopicResponseDto dto = new TopicResponseDto();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setDescription(topic.getDescription());
        return dto;
    }
}
