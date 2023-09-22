package com.example.holidayswap.domain.entity.resort.ResortAmenity;

import com.example.holidayswap.domain.entity.resort.Resort;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resort_resort_amenity", schema = "public")
public class ResortResortAmenity {
    @EmbeddedId
    private ResortResortAmenityId id;
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;
    @MapsId("resort_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;
    @MapsId("inRoomAmenityId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "resort_amenity_id", nullable = false)
    private ResortAmenity resortAmenity;
}