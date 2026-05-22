package com.motocart.audit_microservice.api;

import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.dto.response.AuditLogPreviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

public interface AuditLogResource {
    AuditLogDTO getAuditLog(String auditLogId);

    @GetMapping("/_query")
    Page<AuditLogPreviewDTO> getAuditLogs(Pageable pageable);
}
