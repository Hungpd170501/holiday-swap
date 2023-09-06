package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_tag_id")
    private ServiceTag serviceTag;

    @ManyToMany
    @JoinTable(name = "property_service",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    private Set<Property> properties = new LinkedHashSet<>();

}