package com.demo.kafka.feature.mapping.dto;

import com.demo.kafka.feature.mapping.Mapping;

public class MappingResponseDto {

    private Long id;
    private Long topicId;
    private String sourceColumn;
    private Long targetColumnId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(String sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public Long getTargetColumnId() {
        return targetColumnId;
    }

    public void setTargetColumnId(Long targetColumnId) {
        this.targetColumnId = targetColumnId;
    }

    public static MappingResponseDto fromEntity(Mapping mapping) {
        MappingResponseDto dto = new MappingResponseDto();
        dto.setId(mapping.getId());
        dto.setTopicId(mapping.getTopic().getId());
        dto.setSourceColumn(mapping.getSourceColumn());
        dto.setTargetColumnId(mapping.getTargetColumn().getId());
        return dto;
    }
}

