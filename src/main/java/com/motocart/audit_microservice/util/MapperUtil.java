package com.motocart.audit_microservice.util;

import com.motocart.audit_microservice.document.AuditLogDocument;
import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.dto.response.AuditLogPreviewDTO;
import com.motocart.library.common.event.AuditEvent;

public final class MapperUtil {

    public static AuditLogDocument toAuditLogDocument(AuditEvent auditEvent) {
        return AuditLogDocument.builder()
                .auditLogId(auditEvent.getAuditLogId())
                .entityId(auditEvent.getEntityId())
                .timeStamp(auditEvent.getTimeStamp())
                .action(auditEvent.getAction())
                .entityType(auditEvent.getEntityType())
                .changedFieldsPairMap(auditEvent.getChangedFieldsPairMap())
                .userId(auditEvent.getUserId())
                .sourceService(auditEvent.getSourceService())
                .userRoles(auditEvent.getUserRoles())
                .changeNote(auditEvent.getChangeNote())
                .correlationId(auditEvent.getCorrelationId())
                .build();
    }

    public static AuditLogDTO toAuditLogDTO(AuditLogDocument auditLogDocument) {
        return AuditLogDTO.builder()
                .auditLogId(auditLogDocument.getAuditLogId())
                .entityId(auditLogDocument.getEntityId())
                .timeStamp(auditLogDocument.getTimeStamp())
                .action(auditLogDocument.getAction())
                .entityType(auditLogDocument.getEntityType().toString())
                .changedFieldsPairMap(auditLogDocument.getChangedFieldsPairMap())
                .userId(auditLogDocument.getUserId())
                .sourceService(auditLogDocument.getSourceService())
                .userRoles(auditLogDocument.getUserRoles())
                .changeNote(auditLogDocument.getChangeNote())
                .correlationId(auditLogDocument.getCorrelationId())
                .build();
    }

    public static AuditLogPreviewDTO toAuditLogPreviewDTO(AuditLogDocument auditLogDocument) {
        return AuditLogPreviewDTO.builder()
                .auditLogId(auditLogDocument.getAuditLogId())
                .entityId(auditLogDocument.getEntityId())
                .timeStamp(auditLogDocument.getTimeStamp())
                .action(auditLogDocument.getAction())
                .entityType(auditLogDocument.getEntityType().toString())
                .userId(auditLogDocument.getUserId())
                .sourceService(auditLogDocument.getSourceService())
                .build();
    }
}
