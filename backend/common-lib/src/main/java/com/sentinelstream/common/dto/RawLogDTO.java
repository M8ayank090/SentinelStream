package com.sentinelstream.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Raw log ingestion DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawLogDTO {
    private String id;
    private String source;
    private String message;
    private String level;
    private LocalDateTime timestamp;
    private Map<String, Object> metadata;
    private String sourceIp;
    private String userId;
}
