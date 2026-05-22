package com.motocart.audit_microservice.repository;

import com.motocart.audit_microservice.document.AuditLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLogDocument, String> {

}
