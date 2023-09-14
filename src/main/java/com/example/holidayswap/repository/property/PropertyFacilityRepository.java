package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.facility.PropertyFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyFacilityRepository extends JpaRepository<PropertyFacility, Long> {
}
