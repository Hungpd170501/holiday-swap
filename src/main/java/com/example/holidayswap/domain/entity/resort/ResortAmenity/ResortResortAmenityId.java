package com.example.holidayswap.domain.entity.resort.ResortAmenity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ResortResortAmenityId implements Serializable {
    private static final long serialVersionUID = 6480270893354400353L;
    @NotNull
    @Column(name = "resort_id", nullable = false)
    private Long resortId;

    @NotNull
    @Column(name = "resort_amenity_id", nullable = false)
    private Long resortAmenityId;
}