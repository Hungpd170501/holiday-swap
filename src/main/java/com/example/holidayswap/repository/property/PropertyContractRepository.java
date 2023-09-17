package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyContractRepository extends JpaRepository<PropertyContract, Long> {
}
