package com.sentinelstream.detection.service;

import com.sentinelstream.common.dto.AlertDTO;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ML anomaly detection service client
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AnomalyDetectionClient {

    private final WebClient webClient;

    public AlertDTO scoreWithML(EnrichedLogDTO log) {
        try {
            Double score = webClient.post()
                    .uri("http://ml-anomaly-service:8088/score")
                    .bodyValue(log)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            if (score != null && score > 0.7) { // anomaly threshold
                return AlertDTO.builder()
                        .id(UUID.randomUUID().toString())
                        .ruleId("ML_ANOMALY")
                        .ruleName("ML Anomaly Detection")
                        .severity("HIGH")
                        .type("ANOMALY")
                        .status("OPEN")
                        .description("ML model detected anomalous behavior")
                        .anomalyScore(score)
                        .detectedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .sourceIp(log.getSourceIp())
                        .userId(log.getUserId())
                        .build();
            }
        } catch (Exception e) {
            log.error("ML scoring failed", e);
        }
        return null;
    }
}
