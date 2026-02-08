package com.sentinelstream.enrichment.kafka;

import com.sentinelstream.common.constants.AppConstants;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import com.sentinelstream.common.dto.RawLogDTO;
import com.sentinelstream.common.kafka.KafkaProducerService;
import com.sentinelstream.enrichment.service.EnrichmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for raw logs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LogConsumer {

    private final EnrichmentService enrichmentService;
    private final KafkaProducerService kafkaProducerService;

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_LOGS_RAW, groupId = "enrichment-service")
    public void consumeRawLog(RawLogDTO log) {
        try {
            EnrichedLogDTO enrichedLog = enrichmentService.enrichLog(log);
            kafkaProducerService.publishEnrichedLog(enrichedLog);
            log.info("Log enriched and published: {}", log.getId());
        } catch (Exception e) {
            log.error("Error enriching log: {}", log.getId(), e);
        }
    }
}
