package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.Resort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {
    @Query("select r from Resort r where upper(r.resortName) like upper(concat('%', ?1, '%')) and r.isDeleted = false")
    Page<Resort> findAllByResortNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);

    @Query("select r from Resort r where r.id = ?1 and r.isDeleted = false")
    Optional<Resort> findByIdAndDeletedFalse(Long id);

    @Query("select r from Resort r where upper(r.resortName) like upper(concat('%', ?1, '%')) and r.isDeleted = false")
    Optional<Resort> findByResortNameContainingIgnoreCaseAndDeletedFalse(String name);

    @Query("select r from Resort r where upper(r.resortName) = upper(?1) and r.isDeleted = false")
    Optional<Resort> findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}
