package com.motocart.audit_microservice.repository;

import com.motocart.audit_microservice.document.AuditLogDocument;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLogDocument, String> {

    @Nonnull
    Page<AuditLogDocument> findAll(@Nonnull Pageable pageable);

    @Query("{ 'timeStamp': { $lt: ?0 } }")
    long deleteByTimeStampBefore(Instant date);
}
