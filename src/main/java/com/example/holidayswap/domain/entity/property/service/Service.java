package com.example.holidayswap.domain.entity.property.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service", schema = "public")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private Long id;

    @Column(name = "service_name", length = Integer.MAX_VALUE)
    private String serviceName;

    @Column(name = "service_type_id")
    private Long serviceTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "service_type_id",
            referencedColumnName = "service_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private ServiceType serviceType;

}