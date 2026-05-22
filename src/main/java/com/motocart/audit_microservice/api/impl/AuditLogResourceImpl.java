package com.motocart.audit_microservice.api.impl;

import com.motocart.audit_microservice.api.AuditLogResource;
import com.motocart.audit_microservice.service.AuditService;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.dto.response.AuditLogPreviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@MotocartAPI("/audit")
public class AuditLogResourceImpl implements AuditLogResource {

    private final AuditService auditService;

    public AuditLogResourceImpl(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/_query/{auditLogId}")
    @Override
    public AuditLogDTO getAuditLog(@PathVariable String auditLogId) {
        return auditService.getAuditLog(auditLogId);
    }

    /**
    # First page, 20 records per page
    GET /audit/_query?page=0&size=20

    # Second page, sorted by timestamp descending
    GET /audit/_query?page=1&size=20&sort=timeStamp,desc

    # Multiple sort criteria
    GET /audit/_query?page=0&size=50&sort=timeStamp,desc&sort=action,asc
    */
     @GetMapping("/_query")
    @Override
    public Page<AuditLogPreviewDTO> getAuditLogs(Pageable pageable) {
        return auditService.getAuditLogs(pageable);
    }
}
