package com.sentinelstream.alert.service;

import com.sentinelstream.alert.entity.Alert;
import com.sentinelstream.alert.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Alert service
 */
@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    public Page<Alert> listAlerts(Pageable pageable) {
        return alertRepository.findAll(pageable);
    }

    public Alert getAlertById(String id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }

    public Alert updateAlertStatus(String id, String status) {
        Alert alert = getAlertById(id);
        alert.setStatus(status);
        return alertRepository.save(alert);
    }

    public Alert assignAlert(String id, String assignedTo) {
        Alert alert = getAlertById(id);
        alert.setAssignedTo(assignedTo);
        alert.setStatus("IN_REVIEW");
        return alertRepository.save(alert);
    }

    public Alert addNotes(String id, String notes) {
        Alert alert = getAlertById(id);
        alert.setNotes((alert.getNotes() != null ? alert.getNotes() + "\n" : "") + notes);
        return alertRepository.save(alert);
    }

    public Alert saveAlert(Alert alert) {
        if (alert.getCreatedAt() == null) {
            alert.setCreatedAt(LocalDateTime.now());
        }
        return alertRepository.save(alert);
    }
}
