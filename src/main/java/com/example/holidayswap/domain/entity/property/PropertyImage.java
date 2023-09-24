package com.example.holidayswap.domain.entity.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "property_image")
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
    @Column(name = "property_id")
    private Long propertyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "property_id" ,
            referencedColumnName = "property_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Property property;

}