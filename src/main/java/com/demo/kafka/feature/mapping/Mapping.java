package com.demo.kafka.feature.mapping;

import com.demo.kafka.feature.topic.Topic;
import com.demo.kafka.feature.columns.Columns;
import jakarta.persistence.*;

@Entity
@Table(name = "mappings")
public class Mapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private String sourceColumn;

    @ManyToOne
    @JoinColumn(name = "target_column_id", nullable = false)
    private Columns targetColumn;

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

    public Columns getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(Columns targetColumn) {
        this.targetColumn = targetColumn;
    }
}
