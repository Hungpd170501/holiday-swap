package com.example.holidayswap.domain.entity.property.service;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "service_tag", schema = "public")
public class ServiceTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_tag_id", nullable = false)
    private Long id;

    @Column(name = "service_tag_name", length = Integer.MAX_VALUE)
    private String serviceTagName;

    @OneToMany(mappedBy = "serviceTag")
    private Set<Service> services = new LinkedHashSet<>();

}