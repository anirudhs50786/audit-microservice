package com.motocart.audit_microservice.service;

import com.motocart.audit_microservice.document.AuditLogDocument;
import com.motocart.audit_microservice.repository.AuditLogRepository;
import com.motocart.audit_microservice.util.MapperUtil;
import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.event.AuditEvent;
import com.motocart.library.common.exception.GlobalException;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void addAuditLog(AuditEvent auditEvent) {
        AuditLogDocument auditLog = MapperUtil.toAuditLogDocument(auditEvent);
        auditLogRepository.save(auditLog);
    }

    public AuditLogDTO getAuditLog(String auditLogId) {
        AuditLogDocument auditLogDocument = auditLogRepository.findById(auditLogId).orElseThrow(() -> new GlobalException("Invalid audit log ID."));
        return MapperUtil.toAuditLogDTO(auditLogDocument);
    }
}
