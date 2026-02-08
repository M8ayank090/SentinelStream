package com.sentinelstream.audit.kafka;

import com.sentinelstream.audit.entity.AuditLog;
import com.sentinelstream.audit.service.AuditService;
import com.sentinelstream.common.constants.AppConstants;
import com.sentinelstream.common.dto.AuditLogDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for audit events
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventConsumer {

    private final AuditService auditService;

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_AUDIT_EVENTS, groupId = "audit-service")
    public void consumeAuditEvent(AuditLogDTO auditLogDTO) {
        try {
            AuditLog auditLog = AuditLog.builder()
                    .id(auditLogDTO.getId())
                    .userId(auditLogDTO.getUserId())
                    .username(auditLogDTO.getUsername())
                    .action(auditLogDTO.getAction())
                    .resource(auditLogDTO.getResource())
                    .resourceId(auditLogDTO.getResourceId())
                    .status(auditLogDTO.getStatus())
                    .details(auditLogDTO.getDetails())
                    .ipAddress(auditLogDTO.getIpAddress())
                    .timestamp(auditLogDTO.getTimestamp())
                    .changesBefore(auditLogDTO.getChangesBefore())
                    .changesAfter(auditLogDTO.getChangesAfter())
                    .build();

            auditService.saveAuditLog(auditLog);
            log.info("Audit log stored: {} - {}", auditLogDTO.getAction(), auditLogDTO.getId());
        } catch (Exception e) {
            log.error("Error storing audit log", e);
        }
    }
}
