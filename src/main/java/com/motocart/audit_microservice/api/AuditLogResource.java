package com.motocart.audit_microservice.api;

import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.dto.response.AuditLogPreviewDTO;
import com.motocart.library.common.types.AuditEntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

public interface AuditLogResource {

    AuditLogDTO getAuditLog(String auditLogId);

    @GetMapping("/_query")
    Page<AuditLogPreviewDTO> getAuditLogs(
            @RequestParam(required = false) Integer entityId,
            @RequestParam(required = false) AuditEntityType entityType,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String sourceService,
            @RequestParam(required = false) Instant startTime,
            @RequestParam(required = false) Instant endTime,
            Pageable pageable);
}
