package com.sentinelstream.ingestion.service;

import com.sentinelstream.common.dto.RawLogDTO;
import com.sentinelstream.common.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Log ingestion service processing incoming logs
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LogIngestionService {

    private final KafkaProducerService kafkaProducerService;

    public void processLog(RawLogDTO log) {
        // Assign ID if not present
        if (log.getId() == null) {
            log.setId(UUID.randomUUID().toString());
        }

        // Set timestamp if not present
        if (log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }

        // Validate log
        validateLog(log);

        // Publish to Kafka
        kafkaProducerService.publishRawLog(log);
        log.info("Log ingested: {}", log.getId());
    }

    public void processBatch(List<RawLogDTO> logs) {
        for (RawLogDTO log : logs) {
            processLog(log);
        }
        log.info("Batch of {} logs ingested", logs.size());
    }

    private void validateLog(RawLogDTO log) {
        if (log.getSource() == null || log.getSource().isEmpty()) {
            throw new IllegalArgumentException("Log source is required");
        }
        if (log.getMessage() == null || log.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Log message is required");
        }
    }
}
