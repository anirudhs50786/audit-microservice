package com.motocart.audit_microservice.api.impl;


import com.motocart.library.common.dto.response.CleanUpResponse;
import com.motocart.audit_microservice.service.AuditCleanupService;
import com.motocart.library.common.annotation.MotocartAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@Slf4j
@MotocartAPI("/audit/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AuditCleanupResource {

    private final AuditCleanupService auditCleanupService;

    public AuditCleanupResource(AuditCleanupService auditCleanupService) {
        this.auditCleanupService = auditCleanupService;
    }

    /**     * Manually trigger cleanup with custom retention days     *
     * @param retentionDays Number of days to retain audit logs (default: 90)     * @return CleanupResponse with deleted count and execution time     *
     * Example: POST /audit/admin/cleanup?retentionDays=60     */
    @PostMapping("/cleanup")
    @ResponseStatus(HttpStatus.OK)
    public CleanUpResponse manualCleanup(@RequestParam(defaultValue = "90") int retentionDays) {
        log.info("Manual cleanup triggered with retention days: {}", retentionDays);

        long deletedCount = auditCleanupService.cleanupOldAuditLogs(retentionDays);

        return CleanUpResponse.builder()
                .deletedCount(deletedCount)
                .message("Cleanup completed successfully. Deleted " + deletedCount + " records.")
                .executedAt(Instant.now())
                .build();
    }

    /**     * Cleanup audit logs before a specific date     *
     * @param beforeDate ISO 8601 formatted date (e.g., 2026-01-01T00:00:00Z)     * @return CleanupResponse with deleted count and execution time     *
     * Example: POST /audit/admin/cleanup-by-date?beforeDate=2026-01-01T00:00:00Z     */
    @PostMapping("/cleanup-by-date")
    @ResponseStatus(HttpStatus.OK)
    public CleanUpResponse cleanupByDate(@RequestParam String beforeDate) {
        log.info("Cleanup by date triggered. Before date: {}", beforeDate);

        long deletedCount = auditCleanupService.cleanupBefore(beforeDate);

        return CleanUpResponse.builder()
                .deletedCount(deletedCount)
                .message("Cleanup completed successfully. Deleted " + deletedCount + " records.")
                .executedAt(Instant.now())
                .build();
    }

    /**     * Get current cleanup configuration     *
     * @return Current default retention days     */
    @PostMapping("/cleanup-config")
    @ResponseStatus(HttpStatus.OK)
    public int getCleanupConfig() {
        return auditCleanupService.getDefaultRetentionDays();
    }
}
