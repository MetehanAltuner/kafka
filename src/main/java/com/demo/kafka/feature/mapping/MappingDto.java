package com.demo.kafka.feature.mapping;

import com.demo.kafka.feature.columns.Columns;
import com.demo.kafka.feature.topic.Topic;

public class MappingDto {

    private Long id;
    private Topic topic;
    private String sourceColumn;
    private Columns targetColumn;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(String sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public Columns getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(Columns targetColumn) {
        this.targetColumn = targetColumn;
    }

    public static MappingDto fromEntity(Mapping mapping) {
        MappingDto dto = new MappingDto();
        dto.setId(mapping.getId());
        dto.setTopic(mapping.getTopic());
        dto.setSourceColumn(mapping.getSourceColumn());
        dto.setTargetColumn(mapping.getTargetColumn());
        return dto;
    }

    public Mapping toEntity() {
        Mapping mapping = new Mapping();
        mapping.setId(this.id);
        mapping.setTopic(this.topic);
        mapping.setSourceColumn(this.sourceColumn);
        mapping.setTargetColumn(this.targetColumn);
        return mapping;
    }
}
