package com.demo.kafka.feature.database;

public class DatabaseDto {

    private Long id;
    private String name;
    private String connectionUrl;

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

    public static DatabaseDto fromEntity(Database database) {
        DatabaseDto dto = new DatabaseDto();
        dto.setId(database.getId());
        dto.setName(database.getName());
        dto.setConnectionUrl(database.getConnectionUrl());
        return dto;
    }

    public Database toEntity() {
        Database database = new Database();
        database.setId(this.id);
        database.setName(this.name);
        database.setConnectionUrl(this.connectionUrl);
        return database;
    }
}
