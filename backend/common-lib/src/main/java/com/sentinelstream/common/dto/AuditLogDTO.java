package com.sentinelstream.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Audit log event DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDTO {
    private String id;
    private String userId;
    private String username;
    private String action;
    private String resource;
    private String resourceId;
    private String status;
    private String details;
    private String ipAddress;
    private LocalDateTime timestamp;
    private String changesBefore;
    private String changesAfter;
}
