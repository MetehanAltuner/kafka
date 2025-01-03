package com.demo.kafka.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mappings")
public class Mapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "source_column", nullable = false)
    private String sourceColumn;

    @Column(name = "sink_table", nullable = false)
    private String sinkTable;

    @Column(name = "sink_column", nullable = false)
    private String sinkColumn;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(String sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public String getSinkTable() {
        return sinkTable;
    }

    public void setSinkTable(String sinkTable) {
        this.sinkTable = sinkTable;
    }

    public String getSinkColumn() {
        return sinkColumn;
    }

    public void setSinkColumn(String sinkColumn) {
        this.sinkColumn = sinkColumn;
    }
}
