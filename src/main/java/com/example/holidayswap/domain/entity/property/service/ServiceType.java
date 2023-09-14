package com.example.holidayswap.domain.entity.property.service;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "service_type", schema = "public")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_type_id", nullable = false)
    private Long id;

    @Column(name = "service_name", length = Integer.MAX_VALUE)
    private String serviceName;

    @OneToMany(mappedBy = "serviceType")
    private Set<Service> services = new LinkedHashSet<>();

}