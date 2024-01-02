package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.ResortMaintanceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResortMaintananceImageRepository extends JpaRepository<ResortMaintanceImage, Long> {

}
