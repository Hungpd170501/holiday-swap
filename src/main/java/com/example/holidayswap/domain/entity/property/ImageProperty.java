package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "image_property", schema = "public")
public class ImageProperty {
    @Id
    @Column(name = "image_id", nullable = false)
    private Long id;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;

    @ManyToMany
    @JoinTable(name = "property_images",
            joinColumns = @JoinColumn(name = "property_image_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    private Set<Property> properties = new LinkedHashSet<>();

}