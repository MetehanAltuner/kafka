package com.demo.kafka.feature.tables.dto;

import com.demo.kafka.feature.tables.Tables;

public class TablesDto {

    private Long id;
    private String name;
    private String databaseName;

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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public static TablesDto fromEntity(Tables table) {
        TablesDto dto = new TablesDto();
        dto.setId(table.getId());
        dto.setName(table.getName());
        dto.setDatabaseName(table.getDatabase().getName());
        return dto;
    }
}