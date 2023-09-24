package com.example.holidayswap.repository.resort.amenity;

import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResortAmenityRepository extends JpaRepository<ResortAmenity, Long> {
    @Query("""
            select r from ResortAmenity r
            where upper(r.resortAmenityName) like upper(concat('%', ?1, '%'))
            and r.isDeleted = false""")
    Page<ResortAmenity> findAllByResortAmenityName(String name, Pageable pageable);

    @Query("""
            select r from ResortAmenity r
            where r.resortAmenityTypeId = ?1
            and r.isDeleted = false""")
    Page<ResortAmenity> findAllByResortAmenityTypeId(Long amenityTypeId, Pageable pageable);

    @Query("""
            select r from ResortAmenity r
            where r.resortAmenityTypeId = ?1
            and r.isDeleted = false""")
    List<ResortAmenity> findAllByResortAmenityTypeId(Long amenityTypeId);

    @Query("""
            select r from ResortAmenity r
            join r.resorts rs
            join r.resortAmenityType rType
            where r.resortAmenityTypeId = ?1
            and rs.id = ?2
            and r.isDeleted = false""")
    List<ResortAmenity> findAllByResortAmenityTypeIdAndResortId(Long amenityTypeId, Long resortId);

    @Query("""
            select r from ResortAmenity r
            where r.id = ?1
            and r.isDeleted = false""")
    Optional<ResortAmenity> findByIdAndIsDeletedFalse(Long amenityTypeId);

    @Query("""
            select r from ResortAmenity r
            where upper(r.resortAmenityName)
            like upper(concat('%', ?1, '%'))
            and r.isDeleted = false""")
    Optional<ResortAmenity> findByResortAmenityNameContainingIgnoreCase(String name);
}