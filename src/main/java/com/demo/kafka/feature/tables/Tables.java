package com.demo.kafka.feature.tables;

import com.demo.kafka.feature.columns.Columns;
import com.demo.kafka.feature.database.Database;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tables")
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "database_id", nullable = false)
    private Database database;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<Columns> columns;

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

    public List<Columns> getColumns() {
        return columns;
    }

    public void setColumns(List<Columns> columns) {
        this.columns = columns;
    }
}
