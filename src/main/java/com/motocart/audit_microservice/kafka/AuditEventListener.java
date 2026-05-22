package com.motocart.audit_microservice.kafka;

import com.motocart.audit_microservice.service.AuditService;
import com.motocart.library.common.event.AuditEvent;
import com.motocart.library.kafka.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

    private final AuditService auditService;

    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(topics = KafkaTopics.AUDIT_EVENTS, groupId = "audit-service-group")
    public void listen(AuditEvent auditEvent) {
        auditService.addAuditLog(auditEvent);
    }
}
