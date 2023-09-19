package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTypeRespository extends JpaRepository<PropertyType, Long> {
}
