package com.sentinelstream.indexer.kafka;

import com.sentinelstream.common.constants.AppConstants;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import com.sentinelstream.indexer.service.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for enriched logs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EnrichedLogConsumer {

    private final ElasticsearchService elasticsearchService;

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_LOGS_ENRICHED, groupId = "indexer-service")
    public void consumeEnrichedLog(EnrichedLogDTO log) {
        try {
            elasticsearchService.indexLog(log);
            log.info("Log indexed: {}", log.getId());
        } catch (Exception e) {
            log.error("Error indexing log: {}", log.getId(), e);
        }
    }
}
