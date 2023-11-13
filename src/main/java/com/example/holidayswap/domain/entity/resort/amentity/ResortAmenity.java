package com.example.holidayswap.domain.entity.resort.amentity;

import com.example.holidayswap.domain.entity.resort.Resort;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resort_amenity", schema = "public")
public class ResortAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resort_amenity_id", nullable = false)
    private Long id;
    @NotNull(message = "Resort amenity name must not null.")
    @Column(name = "resort_amenity_name", length = Integer.MAX_VALUE)
    private String resortAmenityName;
    @Length(min = 0, max = 255, message = "Resort amenity description must not 255")
    @Column(name = "resort_amenity_description", length = Integer.MAX_VALUE)
    private String resortAmenityDescription;
    @Column(name = "resort_amenity_link_icon", length = Integer.MAX_VALUE)
    @NotNull
    private String resortAmenityLinkIcon;
    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;
    @Column(name = "resort_amenity_type_id")
    @NotNull
    private Long resortAmenityTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "resort_amenity_type_id",
            referencedColumnName = "resort_amenity_type_id",
            nullable = false,
            insertable = false,
            updatable = false)
    private ResortAmenityType resortAmenityType;
    @ManyToMany
    @JoinTable(
            name = "resorts_amenities",
            joinColumns = @JoinColumn(name = "resort_amenity_id"),
            inverseJoinColumns = @JoinColumn(name = "resort_id"))
    private List<Resort> resorts;
}