package com.demo.kafka.feature.tables;

import com.demo.kafka.feature.database.Database;

public class TablesDto {

    private Long id;
    private String name;
    private Database database;

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

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public static TablesDto fromEntity(Tables table) {
        TablesDto dto = new TablesDto();
        dto.setId(table.getId());
        dto.setName(table.getName());
        dto.setDatabase(table.getDatabase());
        return dto;
    }

    public Tables toEntity() {
        Tables table = new Tables();
        table.setId(this.id);
        table.setName(this.name);
        table.setDatabase(this.database);
        return table;
    }
}
