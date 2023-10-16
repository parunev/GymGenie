package com.genie.gymgenie.models.commons;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAudit<U> {

    @CreatedBy
    @Column(name = "CREATED_BY_USER", length = 120)
    protected U createdBy;

    @CreatedDate
    @Column(name = "CREATION_TIMESTAMP")
    protected LocalDateTime creationTimestamp;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", length = 120)
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_TIMESTAMP")
    protected LocalDateTime lastModifiedTimestamp;
}
