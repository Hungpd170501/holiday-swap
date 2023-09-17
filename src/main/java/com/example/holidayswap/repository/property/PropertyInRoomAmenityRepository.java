package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyInRoomAmenityRepository extends JpaRepository<PropertyInRoomAmenity, Long> {
}
