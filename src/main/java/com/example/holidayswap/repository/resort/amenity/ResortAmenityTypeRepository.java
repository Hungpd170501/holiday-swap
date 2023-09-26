package com.example.holidayswap.repository.resort.amenity;

import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResortAmenityTypeRepository extends JpaRepository<ResortAmenityType, Long> {
    @Query("""
            select r from ResortAmenityType r
            where upper(r.resortAmenityTypeName) like upper(concat('%', ?1, '%')) and r.isDeleted = false""")
    Page<ResortAmenityType> findAllByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);

    @Query("""
            select r from ResortAmenityType r
            join r.resortAmenities rA
            join rA.resorts rs
            where rs.id = ?1 and r.isDeleted = false""")
    List<ResortAmenityType> findAllByResortId(Long resortId);

    @Query("select r from ResortAmenityType r where r.id = ?1 and r.isDeleted = false")
    Optional<ResortAmenityType> findByIdAndIsDeletedFalse(Long id);

    @Query("""
            select r from ResortAmenityType r
            where upper(r.resortAmenityTypeName) like upper(concat('%', ?1, '%')) and r.isDeleted = false""")
    Optional<ResortAmenityType> findByResortAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(String name);

    Optional<ResortAmenityType> findByResortAmenityTypeNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}