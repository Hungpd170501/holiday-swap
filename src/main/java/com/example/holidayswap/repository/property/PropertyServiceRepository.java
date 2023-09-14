package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.service.PropertyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyServiceRepository extends JpaRepository<PropertyService, Long> {
}
