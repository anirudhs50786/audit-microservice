package com.motocart.audit_microservice.api.impl;

import com.motocart.audit_microservice.api.AuditLogResource;
import com.motocart.audit_microservice.service.AuditService;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.AuditLogDTO;
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
}
