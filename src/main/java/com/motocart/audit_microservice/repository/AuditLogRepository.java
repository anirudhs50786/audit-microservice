package com.motocart.audit_microservice.repository;

import com.motocart.audit_microservice.document.AuditLogDocument;
import com.motocart.library.common.types.AuditEntityType;
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

    // Filter by entityId
    Page<AuditLogDocument> findByEntityId(int entityId, Pageable pageable);

    // Filter by entityType
    Page<AuditLogDocument> findByEntityType(AuditEntityType entityType, Pageable pageable);

    // Filter by userId
    Page<AuditLogDocument> findByUserId(int userId, Pageable pageable);

    // Filter by action
    Page<AuditLogDocument> findByAction(String action, Pageable pageable);

    // Filter by sourceService
    Page<AuditLogDocument> findBySourceService(String sourceService, Pageable pageable);

    // Filter by entityId AND entityType
    Page<AuditLogDocument> findByEntityIdAndEntityType(int entityId, AuditEntityType entityType, Pageable pageable);

    // Filter by time range
    Page<AuditLogDocument> findByTimeStampBetween(Instant startTime, Instant endTime, Pageable pageable);

    // Complex query: entityId, userId, and time range
    @Query("{ 'entityId': ?0, 'userId': ?1, 'timeStamp': { $gte: ?2, $lte: ?3 } }")
    Page<AuditLogDocument> findByEntityIdAndUserIdAndTimeRange(int entityId, int userId, Instant startTime, Instant endTime, Pageable pageable);

    @Query("{ 'timeStamp': { $lt: ?0 } }")
    long deleteByTimeStampBefore(Instant date);
}
