package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenity;

public interface PropertyInRoomAmenityService {
    PropertyInRoomAmenity create(Long propertyId, Long inRoomAmenityId);

    PropertyInRoomAmenity update();

    void delete(Long propertyId, Long inRoomAmenityId);
}
