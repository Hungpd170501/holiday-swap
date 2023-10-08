package com.example.holidayswap.domain.entity.resort.amentity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resort_amenity_type", schema = "public")
public class ResortAmenityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_amenity_type_id", nullable = false)
    private Long id;
    @NotNull(message = "Resort amenity type name")
    @Column(name = "resort_amenity_type_name", length = Integer.MAX_VALUE)
    private String resortAmenityTypeName;
    @Length(min = 0, max = 255, message = "")
    @Column(name = "resort_amenity_type_description", length = Integer.MAX_VALUE)
    private String resortAmenityTypeDescription;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "resortAmenityType", fetch = FetchType.LAZY)
    private List<ResortAmenity> resortAmenities;

}