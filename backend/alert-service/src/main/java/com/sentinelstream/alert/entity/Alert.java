package com.sentinelstream.alert.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Alert entity
 */
@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    @Id
    private String id;

    @Column(nullable = false)
    private String ruleId;

    @Column(nullable = false)
    private String ruleName;

    @Column(nullable = false)
    private String severity;

    @Column(nullable = false)
    private String type; // RULE_BASED or ANOMALY

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "alert_entities", joinColumns = @JoinColumn(name = "alert_id"))
    @Column(name = "entity")
    private List<String> affectedEntities;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String createdBy;
    private String assignedTo;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Double anomalyScore;
    private String sourceIp;
    private String userId;
}
