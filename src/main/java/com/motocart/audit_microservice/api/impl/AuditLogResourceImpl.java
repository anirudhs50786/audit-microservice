package com.motocart.audit_microservice.api.impl;

import com.motocart.audit_microservice.api.AuditLogResource;
import com.motocart.audit_microservice.service.AuditService;
import com.motocart.audit_microservice.vo.AuditLogFilterVO;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.AuditLogDTO;
import com.motocart.library.common.dto.response.AuditLogPreviewDTO;
import com.motocart.library.common.types.AuditEntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

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

    /*
    # First page, 20 records per page
    GET /audit/_query?page=0&size=20

    # Second page, sorted by timestamp descending
    GET /audit/_query?page=1&size=20&sort=timeStamp,desc

    # Multiple sort criteria
    GET /audit/_query?page=0&size=50&sort=timeStamp,desc&sort=action,asc
    */
    /**     * Get audit logs with optional filters and pagination     *
     * Query Examples:
     *  GET /audit/_query?page=0&size=20
     *  GET /audit/_query?entityId=123&page=0&size=20
     *  GET /audit/_query?entityId=123&entityType=PRODUCT&page=0&size=20&sort=timeStamp,desc
     *  GET /audit/_query?userId=5&page=0&size=20     * GET /audit/_query?action=UPDATE&page=0&size=20
     *  GET /audit/_query?sourceService=product-service&page=0&size=20
     *  GET /audit/_query?startTime=2026-01-01T00:00:00Z&endTime=2026-12-31T23:59:59Z&page=0&size=20
     *  GET /audit/_query?entityId=123&userId=5&startTime=2026-01-01T00:00:00Z&endTime=2026-12-31T23:59:59Z&page=0&size=20
     *  */
    @GetMapping("/_query")
    @Override
    public Page<AuditLogPreviewDTO> getAuditLogs(
            @RequestParam(required = false) Integer entityId,
            @RequestParam(required = false) AuditEntityType entityType,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String sourceService,
            @RequestParam(required = false) Instant startTime,
            @RequestParam(required = false) Instant endTime,
            Pageable pageable) {

        AuditLogFilterVO filter = AuditLogFilterVO.builder()
                .entityId(entityId)
                .entityType(entityType)
                .userId(userId)
                .action(action)
                .sourceService(sourceService)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        return auditService.getAuditLogs(filter, pageable);
    }
}
