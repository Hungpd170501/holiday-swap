package com.example.holidayswap.repository.address;

import com.example.holidayswap.domain.entity.address.StateOrProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateOrProvinceRepository extends JpaRepository<StateOrProvince, Long> {
    Optional<StateOrProvince> findByCodeEquals(String code);
}
