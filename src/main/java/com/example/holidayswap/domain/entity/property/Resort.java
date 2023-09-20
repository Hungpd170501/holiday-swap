package com.example.holidayswap.domain.entity.property;

import com.example.holidayswap.domain.entity.address.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @Column(name = "address_id")
    private Long addressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "address_id",
            referencedColumnName = "address_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private Address address;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

//    @OneToMany(mappedBy = "resort")
//    private List<Property> properties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "resort")
    private List<ResortImage> resortImages;

}