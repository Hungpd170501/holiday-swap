package com.example.holidayswap.domain.dto.response.property.inRoomAmenity;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class InRoomAmenityTypeResponse {
    private Long id;
    private String inRoomAmenityTypeName;
    private Boolean isDeleted;
    private Set<InRoomAmenity> inRoomAmenities = new LinkedHashSet<>();
}
