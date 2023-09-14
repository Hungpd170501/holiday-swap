package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.property.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {
}
