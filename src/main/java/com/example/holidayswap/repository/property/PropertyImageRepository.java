package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    @Query("select p from PropertyImage p where p.propertyId = ?1 and p.isDeleted = false")
    List<PropertyImage> findAllByPropertyIdAndDeletedIsFalse(Long propertyId);

    @Query("select p from PropertyImage p where p.id = ?1 and p.isDeleted = false")
    Optional<PropertyImage> findByIdAndDeletedIsFalse(Long propertyId);
}
