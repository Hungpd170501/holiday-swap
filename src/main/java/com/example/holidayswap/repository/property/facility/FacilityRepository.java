package com.example.holidayswap.repository.property.facility;

import com.example.holidayswap.domain.entity.property.facility.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
