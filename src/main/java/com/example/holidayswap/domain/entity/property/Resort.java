package com.example.holidayswap.domain.entity.property;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "resort")
public class Resort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "resort_name")
    private String resortName;

    //    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "address_id")
    @Column(name = "address_id")
    private Long address;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

//    @OneToMany(mappedBy = "resort")
//    private Set<Property> properties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "resort")
    private Set<ResortImage> resortImages = new LinkedHashSet<>();

}