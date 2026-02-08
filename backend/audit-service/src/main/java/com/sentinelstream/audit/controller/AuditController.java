package com.sentinelstream.audit.controller;

import com.sentinelstream.audit.entity.AuditLog;
import com.sentinelstream.audit.service.AuditService;
import com.sentinelstream.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Audit REST endpoint
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/logs")
    public ResponseEntity<ApiResponse<Page<AuditLog>>> listAuditLogs(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(auditService.listAuditLogs(pageable)));
    }

    @GetMapping("/logs/user/{userId}")
    public ResponseEntity<ApiResponse<Page<AuditLog>>> getUserAuditLogs(
            @PathVariable String userId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(auditService.getUserAuditLogs(userId, pageable)));
    }

    @GetMapping("/logs/action/{action}")
    public ResponseEntity<ApiResponse<Page<AuditLog>>> getActionAuditLogs(
            @PathVariable String action, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(auditService.getActionAuditLogs(action, pageable)));
    }
}
