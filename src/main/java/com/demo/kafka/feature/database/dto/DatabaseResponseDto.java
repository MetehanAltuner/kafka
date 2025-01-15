package com.demo.kafka.feature.database.dto;

import com.demo.kafka.feature.database.Database;

public class DatabaseResponseDto {

    private Long id;
    private String name;
    private String connectionUrl;

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

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public static DatabaseResponseDto fromEntity(Database database) {
        DatabaseResponseDto dto = new DatabaseResponseDto();
        dto.setId(database.getId());
        dto.setName(database.getName());
        dto.setConnectionUrl(database.getConnectionUrl());
        return dto;
    }
}


