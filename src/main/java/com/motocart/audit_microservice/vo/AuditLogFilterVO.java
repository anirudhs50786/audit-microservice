package com.motocart.audit_microservice.vo;

import com.motocart.library.common.types.AuditEntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogFilterVO {
    // Entity filters
    private Integer entityId;
    private AuditEntityType entityType;

    // User filters
    private Integer userId;

    // Action filters
    private String action;

    // Service filter
    private String sourceService;

    // Time range filters
    private Instant startTime;
    private Instant endTime;
}
