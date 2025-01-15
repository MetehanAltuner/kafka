package com.demo.kafka.feature.columns.dto;

import com.demo.kafka.feature.columns.Columns;

public class ColumnsResponseDto {

    private Long id;
    private String name;
    private boolean isPrimaryKey;
    private Long tableId;

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

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public static ColumnsResponseDto fromEntity(Columns column) {
        ColumnsResponseDto dto = new ColumnsResponseDto();
        dto.setId(column.getId());
        dto.setName(column.getName());
        dto.setPrimaryKey(column.isPrimaryKey());
        dto.setTableId(column.getTable().getId());
        return dto;
    }
}
