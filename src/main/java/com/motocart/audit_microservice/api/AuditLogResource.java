package com.motocart.audit_microservice.api;

import com.motocart.library.common.dto.AuditLogDTO;

public interface AuditLogResource {
    AuditLogDTO getAuditLog(String auditLogId);
}
