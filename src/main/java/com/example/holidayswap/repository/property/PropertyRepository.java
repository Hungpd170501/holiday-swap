package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p " +
            "join p.propertyType t " +
            "join  t.resorts r " +
            "where r.id = ?1 and r.isDeleted = false and t.isDeleted = false and p.isDeleted = false")
    Page<Property> findAllByResortIdAndIsDeleteIsFalse(Long resortId, Pageable pageable);

    @Query("select p from Property p where p.id = ?1 and p.isDeleted = false ")
    Optional<Property> findPropertyById(Long propertyId);
}
