package com.sentinelstream.common.kafka;

import com.sentinelstream.common.dto.AlertDTO;
import com.sentinelstream.common.dto.AuditLogDTO;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import com.sentinelstream.common.dto.RawLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Kafka producer service for publishing events
 */
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishRawLog(RawLogDTO log) {
        String topic = "logs.raw";
        String key = log.getId() != null ? log.getId() : UUID.randomUUID().toString();
        kafkaTemplate.send(topic, key, log);
    }

    public void publishEnrichedLog(EnrichedLogDTO log) {
        String topic = "logs.enriched";
        String key = log.getId() != null ? log.getId() : UUID.randomUUID().toString();
        kafkaTemplate.send(topic, key, log);
    }

    public void publishAlert(AlertDTO alert) {
        String topic = "alerts.generated";
        String key = alert.getId() != null ? alert.getId() : UUID.randomUUID().toString();
        kafkaTemplate.send(topic, key, alert);
    }

    public void publishAuditLog(AuditLogDTO auditLog) {
        String topic = "audit.events";
        String key = auditLog.getId() != null ? auditLog.getId() : UUID.randomUUID().toString();
        kafkaTemplate.send(topic, key, auditLog);
    }
}
