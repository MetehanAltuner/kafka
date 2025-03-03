package com.demo.kafka.feature.columns;

import com.demo.kafka.feature.tables.Tables;
import jakarta.persistence.*;

@Entity
@Table(name = "columns")
public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private Tables table;

    @Column(nullable = false)
    private boolean isprimarykey;

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

    public boolean isPrimaryKey() {
        return isprimarykey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isprimarykey = primaryKey;
    }
}

