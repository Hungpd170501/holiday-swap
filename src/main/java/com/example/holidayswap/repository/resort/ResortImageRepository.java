package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.ResortImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResortImageRepository extends JpaRepository<ResortImage, Long> {
    @Query("select r from ResortImage r where r.isDeleted = false and r.resortId = ?1")
    List<ResortImage> findAllByResortId(Long resortId);

    @Query("select r from ResortImage r where r.id = ?1 and r.isDeleted = false ")
    Optional<ResortImage> findByIdAndDeletedFalse(Long resortId);
//    @Query("select r from ResortImage r where r.id = ?1 ")
//    Optional<ResortImage> findById(Long resortId);
}
