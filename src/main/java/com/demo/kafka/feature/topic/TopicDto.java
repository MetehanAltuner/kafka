package com.demo.kafka.feature.topic;

public class TopicDto {

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

    public static TopicDto fromEntity(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setDescription(topic.getDescription());
        return dto;
    }

    public Topic toEntity() {
        Topic topic = new Topic();
        topic.setId(this.id);
        topic.setName(this.name);
        topic.setDescription(this.description);
        return topic;
    }
}
