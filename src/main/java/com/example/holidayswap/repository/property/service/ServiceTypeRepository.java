package com.example.holidayswap.repository.property.service;

import com.example.holidayswap.domain.entity.property.service.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
