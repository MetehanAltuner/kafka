package com.demo.kafka.feature.tables.dto;
public class TablesRequestDto {
    private String name;
    private Long databaseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
}
