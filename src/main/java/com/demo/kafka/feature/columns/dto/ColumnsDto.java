package com.demo.kafka.feature.columns.dto;

import com.demo.kafka.feature.columns.Columns;
import com.demo.kafka.feature.tables.Tables;

public class ColumnsDto {

    private Long id;
    private String name;
    private Tables table;

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

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public static ColumnsDto fromEntity(Columns column) {
        ColumnsDto dto = new ColumnsDto();
        dto.setId(column.getId());
        dto.setName(column.getName());
        dto.setTable(column.getTable());
        return dto;
    }

    public Columns toEntity() {
        Columns column = new Columns();
        column.setId(this.id);
        column.setName(this.name);
        column.setTable(this.table);
        return column;
    }
}
