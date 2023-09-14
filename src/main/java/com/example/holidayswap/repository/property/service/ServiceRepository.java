package com.example.holidayswap.repository.property.service;

import com.example.holidayswap.domain.entity.property.service.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}