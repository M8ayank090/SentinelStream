package com.sentinelstream.enrichment.service;

import com.sentinelstream.common.constants.AppConstants;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import com.sentinelstream.common.dto.RawLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Log enrichment service
 */
@Service
@Slf4j
public class EnrichmentService {

    public EnrichedLogDTO enrichLog(RawLogDTO rawLog) {
        long startTime = System.currentTimeMillis();

        EnrichedLogDTO enriched = EnrichedLogDTO.builder()
                .id(rawLog.getId())
                .source(rawLog.getSource())
                .message(rawLog.getMessage())
                .severity(mapSeverity(rawLog.getLevel()))
                .timestamp(rawLog.getTimestamp())
                .normalizedTimestamp(normalizeTimestamp(rawLog.getTimestamp()))
                .metadata(rawLog.getMetadata())
                .sourceIp(rawLog.getSourceIp())
                .userId(rawLog.getUserId())
                .processingTimeMs(System.currentTimeMillis() - startTime)
                .build();

        // Perform GeoIP enrichment if source IP available
        if (rawLog.getSourceIp() != null && !rawLog.getSourceIp().isEmpty()) {
            enrichGeolocation(enriched);
        }

        log.debug("Log enriched: {} -> {}", rawLog.getId(), enriched.getSeverity());
        return enriched;
    }

    private String mapSeverity(String level) {
        if (level == null) {
            return AppConstants.SEVERITY_INFO;
        }
        
        return switch (level.toUpperCase()) {
            case "FATAL", "CRITICAL" -> AppConstants.SEVERITY_CRITICAL;
            case "ERROR" -> AppConstants.SEVERITY_HIGH;
            case "WARN", "WARNING" -> AppConstants.SEVERITY_MEDIUM;
            case "DEBUG" -> AppConstants.SEVERITY_LOW;
            case "INFO" -> AppConstants.SEVERITY_INFO;
            default -> AppConstants.SEVERITY_INFO;
        };
    }

    private String normalizeTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return formatter.format(timestamp);
    }

    private void enrichGeolocation(EnrichedLogDTO enriched) {
        // TODO: Implement GeoIP lookup
        // Mock implementation for now
        enriched.setSourceCountryCode("US");
        enriched.setSourceCountry("United States");
    }
}
