package com.sentinelstream.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Security alert DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDTO {
    private String id;
    private String ruleId;
    private String ruleName;
    private String severity;
    private String type; // RULE_BASED or ANOMALY
    private String status;
    private String description;
    private List<String> affectedEntities;
    private LocalDateTime detectedAt;
    private LocalDateTime createdAt;
    private String createdBy;
    private String assignedTo;
    private String notes;
    private Double anomalyScore;
    private String sourceIp;
    private String userId;
}
