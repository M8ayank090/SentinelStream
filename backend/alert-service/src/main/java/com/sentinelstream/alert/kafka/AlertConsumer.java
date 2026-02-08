package com.sentinelstream.alert.kafka;

import com.sentinelstream.alert.entity.Alert;
import com.sentinelstream.alert.service.AlertService;
import com.sentinelstream.common.constants.AppConstants;
import com.sentinelstream.common.dto.AlertDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for generated alerts
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AlertConsumer {

    private final AlertService alertService;

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_ALERTS_GENERATED, groupId = "alert-service")
    public void consumeAlert(AlertDTO alertDTO) {
        try {
            Alert alert = Alert.builder()
                    .id(alertDTO.getId())
                    .ruleId(alertDTO.getRuleId())
                    .ruleName(alertDTO.getRuleName())
                    .severity(alertDTO.getSeverity())
                    .type(alertDTO.getType())
                    .status(alertDTO.getStatus())
                    .description(alertDTO.getDescription())
                    .affectedEntities(alertDTO.getAffectedEntities())
                    .detectedAt(alertDTO.getDetectedAt())
                    .createdAt(alertDTO.getCreatedAt())
                    .anomalyScore(alertDTO.getAnomalyScore())
                    .sourceIp(alertDTO.getSourceIp())
                    .userId(alertDTO.getUserId())
                    .build();

            alertService.saveAlert(alert);
            log.info("Alert stored: {}", alertDTO.getId());
        } catch (Exception e) {
            log.error("Error storing alert", e);
        }
    }
}
