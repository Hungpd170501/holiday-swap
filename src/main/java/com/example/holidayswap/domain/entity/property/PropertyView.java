package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_view", schema = "public")
public class PropertyView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_view_id", nullable = false)
    private Long id;
    @Column(name = "property_view_name", length = Integer.MAX_VALUE)
    private String propertyViewName;
    @Column(name = "property_view_description", length = Integer.MAX_VALUE)
    private String propertyViewDescription;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

}
