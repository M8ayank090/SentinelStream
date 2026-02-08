package com.sentinelstream.detection.kafka;

import com.sentinelstream.common.constants.AppConstants;
import com.sentinelstream.common.dto.AlertDTO;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import com.sentinelstream.common.kafka.KafkaProducerService;
import com.sentinelstream.detection.service.DetectionEngine;
import com.sentinelstream.detection.service.AnomalyDetectionClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Kafka consumer for enriched logs - performs threat detection
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EnrichedLogConsumer {

    private final DetectionEngine detectionEngine;
    private final AnomalyDetectionClient anomalyDetectionClient;
    private final KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_LOGS_ENRICHED, groupId = "detection-service")
    public void consumeEnrichedLog(EnrichedLogDTO log) {
        try {
            // Rule-based detection
            List<AlertDTO> ruleAlerts = detectionEngine.detectThreats(log);
            for (AlertDTO alert : ruleAlerts) {
                kafkaProducerService.publishAlert(alert);
            }

            // ML-based anomaly detection
            AlertDTO mlAlert = anomalyDetectionClient.scoreWithML(log);
            if (mlAlert != null) {
                kafkaProducerService.publishAlert(mlAlert);
            }

            log.debug("Threats detected for log: {}", log.getId());
        } catch (Exception e) {
            log.error("Error detecting threats for log: {}", log.getId(), e);
        }
    }
}
