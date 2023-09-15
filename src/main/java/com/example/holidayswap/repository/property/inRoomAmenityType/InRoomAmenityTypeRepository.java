package com.example.holidayswap.repository.property.inRoomAmenityType;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenityType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface InRoomAmenityTypeRepository extends JpaRepository<InRoomAmenityType, Long> {
    @Query("SELECT a FROM InRoomAmenityType a " +
            "JOIN a.inRoomAmenities b " +
            "JOIN b.propertyInRoomAmenities c " +
            "JOIN c.property d " +
            "WHERE d.id = :id")
    Set<InRoomAmenityType> findFacilitiesTypesByPropertyId(@Param("id") Long id);

    Page<InRoomAmenityType> findAllByInRoomAmenityTypeNameContainingIgnoreCaseAndIsDeletedIsFalse(String name, Pageable pageable);

    Optional<InRoomAmenityType> findByIdAndIsDeletedIsFalse(Long id);
}
