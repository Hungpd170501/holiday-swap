package com.example.holidayswap.domain.entity.common;

import com.example.holidayswap.listener.CustomAuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(CustomAuditingEntityListener.class)
public class BaseEntityAudit {

    @CreationTimestamp
    private LocalDateTime createdOn;

    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    private LocalDateTime lastModifiedOn;

    @LastModifiedBy
    private String lastModifiedBy;
}