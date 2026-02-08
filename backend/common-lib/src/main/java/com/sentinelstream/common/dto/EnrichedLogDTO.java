package com.sentinelstream.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Enriched log DTO after processing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrichedLogDTO {
    private String id;
    private String source;
    private String message;
    private String severity;
    private LocalDateTime timestamp;
    private String normalizedTimestamp;
    private Map<String, Object> metadata;
    private String sourceIp;
    private String sourceCountryCode;
    private String sourceCountry;
    private String userId;
    private String userAgent;
    private String requestPath;
    private int responseCode;
    private Long processingTimeMs;
}
