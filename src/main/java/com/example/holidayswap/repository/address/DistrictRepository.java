package com.example.holidayswap.repository.address;

import com.example.holidayswap.domain.entity.address.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByCodeEquals(String code);
}
