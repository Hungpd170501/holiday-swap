package com.example.holidayswap.domain.entity.resort;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "image_resort", schema = "public")
public class ImageResort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_resort_id", nullable = false)
    private Long id;

    @Column(name = "link", length = Integer.MAX_VALUE)
    private String link;

    @ManyToMany
    @JoinTable(name = "resort_images",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "resort_id"))
    private Set<Resort> resorts = new LinkedHashSet<>();

}