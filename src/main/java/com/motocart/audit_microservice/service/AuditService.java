package com.motocart.audit_microservice.service;

import com.motocart.audit_microservice.document.AuditLogDocument;
import com.motocart.audit_microservice.repository.AuditLogRepository;
import com.motocart.audit_microservice.util.MapperUtil;
import com.motocart.audit_microservice.vo.AuditLogFilterVO;
import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.dto.response.AuditLogPreviewDTO;
import com.motocart.library.common.event.AuditEvent;
import com.motocart.library.common.exception.GlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

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

    /**
     *  Get audit logs with optional filters and pagination
     */
    public Page<AuditLogPreviewDTO> getAuditLogs(AuditLogFilterVO filter, Pageable pageable) {
        Page<AuditLogDocument> auditLogs;

        // If no filters, return all
        if (isEmptyFilter(filter)) {
            auditLogs = auditLogRepository.findAll(pageable);
        }
        // Entity ID only
        else if (filter.getEntityId() != null && filter.getEntityType() == null && filter.getUserId() == null && filter.getStartTime() == null) {
            auditLogs = auditLogRepository.findByEntityId(filter.getEntityId(), pageable);
        }
        // Entity Type only
        else if (filter.getEntityType() != null && filter.getEntityId() == null && filter.getUserId() == null && filter.getStartTime() == null) {
            auditLogs = auditLogRepository.findByEntityType(filter.getEntityType(), pageable);
        }
        // Entity ID + Entity Type
        else if (filter.getEntityId() != null && filter.getEntityType() != null && filter.getUserId() == null && filter.getStartTime() == null) {
            auditLogs = auditLogRepository.findByEntityIdAndEntityType(filter.getEntityId(), filter.getEntityType(), pageable);
        }
        // User ID only
        else if (filter.getUserId() != null && filter.getEntityId() == null && filter.getStartTime() == null) {
            auditLogs = auditLogRepository.findByUserId(filter.getUserId(), pageable);
        }
        // Action only
        else if (filter.getAction() != null && filter.getEntityId() == null && filter.getUserId() == null && filter.getStartTime() == null) {
            auditLogs = auditLogRepository.findByAction(filter.getAction(), pageable);
        }
        // Source Service only
        else if (filter.getSourceService() != null && filter.getEntityId() == null && filter.getUserId() == null && filter.getStartTime() == null) {
            auditLogs = auditLogRepository.findBySourceService(filter.getSourceService(), pageable);
        }
        // Time range only
        else if (filter.getStartTime() != null && filter.getEndTime() != null && filter.getEntityId() == null && filter.getUserId() == null) {
            Instant endTime = filter.getEndTime();
            auditLogs = auditLogRepository.findByTimeStampBetween(filter.getStartTime(), endTime, pageable);
        }
        // Entity ID + User ID + Time range (complex query)
        else if (filter.getEntityId() != null && filter.getUserId() != null && filter.getStartTime() != null && filter.getEndTime() != null) {
            auditLogs = auditLogRepository.findByEntityIdAndUserIdAndTimeRange(
                    filter.getEntityId(), filter.getUserId(), filter.getStartTime(), filter.getEndTime(), pageable);
        }
        // Default fallback: all records
        else {
            auditLogs = auditLogRepository.findAll(pageable);
        }

        return auditLogs.map(MapperUtil::toAuditLogPreviewDTO);
    }

    /**
     *  Check if filter is empty (all fields null)
     *
     */
    private boolean isEmptyFilter(AuditLogFilterVO filter) {
        return filter.getEntityId() == null
                && filter.getEntityType() == null
                && filter.getUserId() == null
                && filter.getAction() == null
                && filter.getSourceService() == null
                && filter.getStartTime() == null
                && filter.getEndTime() == null;
    }

}
