package com.sentinelstream.alert.controller;

import com.sentinelstream.alert.entity.Alert;
import com.sentinelstream.alert.service.AlertService;
import com.sentinelstream.common.dto.ApiResponse;
import com.sentinelstream.common.dto.AlertDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Alert REST endpoint
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<Alert>>> listAlerts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(alertService.listAlerts(pageable)));
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<ApiResponse<Alert>> getAlert(@PathVariable String alertId) {
        return ResponseEntity.ok(ApiResponse.success(alertService.getAlertById(alertId)));
    }

    @PutMapping("/{alertId}/status")
    public ResponseEntity<ApiResponse<Alert>> updateStatus(@PathVariable String alertId, @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success(alertService.updateAlertStatus(alertId, status)));
    }

    @PutMapping("/{alertId}/assign")
    public ResponseEntity<ApiResponse<Alert>> assignAlert(@PathVariable String alertId, @RequestParam String assignedTo) {
        return ResponseEntity.ok(ApiResponse.success(alertService.assignAlert(alertId, assignedTo)));
    }

    @PostMapping("/{alertId}/notes")
    public ResponseEntity<ApiResponse<Alert>> addNotes(@PathVariable String alertId, @RequestParam String notes) {
        return ResponseEntity.ok(ApiResponse.success(alertService.addNotes(alertId, notes)));
    }
}
