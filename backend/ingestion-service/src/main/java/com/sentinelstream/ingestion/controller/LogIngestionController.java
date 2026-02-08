package com.sentinelstream.ingestion.controller;

import com.sentinelstream.common.dto.ApiResponse;
import com.sentinelstream.common.dto.RawLogDTO;
import com.sentinelstream.ingestion.service.LogIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Log ingestion REST endpoint
 */
@RestController
@RequestMapping("/api/ingest")
@RequiredArgsConstructor
public class LogIngestionController {

    private final LogIngestionService ingestionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> ingestLog(@RequestBody RawLogDTO log) {
        ingestionService.processLog(log);
        return ResponseEntity.accepted().body(
                ApiResponse.success(Map.of("status", "accepted", "logId", log.getId()))
        );
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<Map<String, Object>>> ingestBatch(@RequestBody List<RawLogDTO> logs) {
        ingestionService.processBatch(logs);
        return ResponseEntity.accepted().body(
                ApiResponse.success(Map.of("status", "accepted", "count", logs.size()))
        );
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Ingestion service is healthy"));
    }
}
