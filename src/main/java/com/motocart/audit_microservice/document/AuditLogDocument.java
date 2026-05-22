package com.motocart.audit_microservice.document;

import com.motocart.library.common.event.AuditEvent;
import com.motocart.library.common.types.AuditEntityType;
import com.motocart.library.common.types.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Document("audit_logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDocument {
    @Id
    private String auditLogId;
    private int entityId;
    @Indexed
    private Instant timeStamp;
    private String action;
    private AuditEntityType entityType;
    private Map<String, AuditEvent.FieldChangePair> changedFieldsPairMap;
    private int userId;
    private String sourceService;
    private List<Roles> userRoles;
    private String changeNote;
    private String correlationId;

}
