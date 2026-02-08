package com.sentinelstream.detection.service;

import com.sentinelstream.common.dto.AlertDTO;
import com.sentinelstream.common.dto.EnrichedLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Rule-based detection engine
 */
@Service
@Slf4j
public class DetectionEngine {

    public List<AlertDTO> detectThreats(EnrichedLogDTO log) {
        List<AlertDTO> alerts = new ArrayList<>();

        // Rule 1: Brute force attempts (multiple failed logins)
        if (isBruteForceAttempt(log)) {
            alerts.add(createAlert(log, "BRUTE_FORCE", "Brute force login attempt detected", "HIGH"));
        }

        // Rule 2: Suspicious IP patterns
        if (isSuspiciousIp(log)) {
            alerts.add(createAlert(log, "SUSPICIOUS_IP", "Access from suspicious IP address", "MEDIUM"));
        }

        // Rule 3: Unusual traffic spike
        if (isUnusualTrafficSpike(log)) {
            alerts.add(createAlert(log, "TRAFFIC_SPIKE", "Unusual traffic spike detected", "MEDIUM"));
        }

        // Rule 4: Failed login bursts
        if (isFailedLoginBurst(log)) {
            alerts.add(createAlert(log, "LOGIN_BURST", "Multiple failed login attempts detected", "HIGH"));
        }

        return alerts;
    }

    private boolean isBruteForceAttempt(EnrichedLogDTO log) {
        // Check if log contains authentication failure details
        return log.getMessage().toLowerCase().contains("authentication failed") ||
               log.getMessage().toLowerCase().contains("invalid credentials");
    }

    private boolean isSuspiciousIp(EnrichedLogDTO log) {
        // Mock: check for private/reserved IPs or known malicious ranges
        String ip = log.getSourceIp();
        if (ip == null) return false;
        return ip.startsWith("192.168.") || ip.startsWith("10.");
    }

    private boolean isUnusualTrafficSpike(EnrichedLogDTO log) {
        // Mock: this would be stateful and check against baseline
        return log.getResponseCode() >= 400 && log.getResponseCode() < 500;
    }

    private boolean isFailedLoginBurst(EnrichedLogDTO log) {
        return log.getMessage().toLowerCase().contains("login failed") &&
               log.getSeverity().equals("HIGH");
    }

    private AlertDTO createAlert(EnrichedLogDTO log, String ruleId, String description, String severity) {
        return AlertDTO.builder()
                .id(UUID.randomUUID().toString())
                .ruleId(ruleId)
                .ruleName(ruleId)
                .severity(severity)
                .type("RULE_BASED")
                .status("OPEN")
                .description(description)
                .detectedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .sourceIp(log.getSourceIp())
                .userId(log.getUserId())
                .affectedEntities(List.of(log.getSource()))
                .build();
    }
}
