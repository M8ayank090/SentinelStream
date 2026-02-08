package com.sentinelstream.alert.repository;

import com.sentinelstream.alert.entity.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Alert repository
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, String> {
    Page<Alert> findByStatus(String status, Pageable pageable);
    Page<Alert> findBySeverity(String severity, Pageable pageable);
    List<Alert> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
    List<Alert> findByAssignedTo(String assignedTo);
}
