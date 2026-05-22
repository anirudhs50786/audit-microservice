package com.motocart.audit_microservice.service;

import com.motocart.audit_microservice.repository.AuditLogRepository;
import com.motocart.library.common.exception.GlobalException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
@Getter
public class AuditCleanupService {

    private final AuditLogRepository auditLogRepository;

    @Value("${audit.retention.days:90}")
    private int defaultRetentionDays;

    public AuditCleanupService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     *  Scheduled cleanup task - runs daily at 2 AM
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void scheduleCleanup() {
        try {
            log.info("Starting scheduled cleanup with retention period of {} days", defaultRetentionDays);
            cleanupOldAuditLogs(defaultRetentionDays);
        } catch (Exception e) {
            log.error("Error during scheduled cleanup", e);
        }
    }

    /**     * Clean up audit logs older than specified days     */
    public long cleanupOldAuditLogs(int retentionDays) {
        if (retentionDays < 0) {
            throw new GlobalException("Retention days must be positive");
        }

        Instant cutoffDate = Instant.now().minus(Duration.ofDays(retentionDays));
        long deletedCount = auditLogRepository.deleteByTimeStampBefore(cutoffDate);

        log.info("Cleanup completed. Deleted {} audit logs older than {} days (before {})",
                deletedCount, retentionDays, cutoffDate);

        return deletedCount;
    }

    /**     * Clean up audit logs before a specific date     */
    public long cleanupBefore(String isoDate) {
        try {
            Instant cutoffDate = Instant.parse(isoDate);
            long deletedCount = auditLogRepository.deleteByTimeStampBefore(cutoffDate);

            log.info("Cleanup completed. Deleted {} audit logs before {}", deletedCount, isoDate);

            return deletedCount;
        } catch (IllegalArgumentException e) {
            throw new GlobalException("Invalid date format. Use ISO 8601 format (e.g., 2026-01-01T00:00:00Z)");
        }
    }

}
