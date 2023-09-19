package com.example.holidayswap.repository.property.inRoomAmenity;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InRoomAmenityRepository extends JpaRepository<InRoomAmenity, Long> {
    @Query("""
            select i from InRoomAmenity i
            where upper(i.inRoomAmenitiesName) like upper(concat('%', ?1, '%')) and i.inRoomAmenitiesTypeId = ?2 and i.isDeleted = false""")
    Page<InRoomAmenity> findInRoomAmenitiesByInRoomAmenitiesNameContainingIgnoreCaseAndAndInRoomAmenitiesTypeIdAndIsDeletedIsFalse
            (String searchName, Long InRoomAmenityTypeId, Pageable pageable);

    @Query(""" 
            select i from InRoomAmenity i
            join i.propertyInRoomAmenities pI
            join  pI.property p 
            where p.id = :propertyId and i.isDeleted = false""")
    List<InRoomAmenity> findInRoomAmenitiesByPropertyId(@Param("propertyId") Long propertyId);

    @Query(""" 
            select i from InRoomAmenity i
            join i.propertyInRoomAmenities pI
            join  pI.property p 
            where p.id = :propertyId and i.isDeleted = false and i.inRoomAmenitiesTypeId = :inRoomAmenityTypeId""")
    List<InRoomAmenity> findInRoomAmenitiesByPropertyIdAndInRoomAmenityTypeId(@Param("propertyId") Long propertyId, @Param("inRoomAmenityTypeId") Long inRoomAmenityTypeId);

    @Query("select i from InRoomAmenity i where i.id = ?1 and i.isDeleted = false")
    Optional<InRoomAmenity> findInRoomAmenityByIdAndIsDeletedIsFalse(Long id);
}
