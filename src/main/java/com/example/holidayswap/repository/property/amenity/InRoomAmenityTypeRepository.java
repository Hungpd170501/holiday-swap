package com.example.holidayswap.repository.property.amenity;

import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InRoomAmenityTypeRepository extends JpaRepository<InRoomAmenityType, Long> {
    @Query("""
            select r from InRoomAmenityType r
            where upper(r.inRoomAmenityTypeName) like upper(concat('%', ?1, '%')) and r.isDeleted = false""")
    Page<InRoomAmenityType> findAllByInRoomAmenityTypeNameContainingIgnoreCaseAndIsDeletedFalse(String name, Pageable pageable);

    @Query("""
            select iType from InRoomAmenityType iType
            join iType.inRoomAmenities i
            join i.properties p
            where i.id = ?1 and iType.isDeleted = false""")
    List<InRoomAmenityType> findAllByResortId(Long propertyId);

    @Query("select iType from InRoomAmenityType iType where iType.id = ?1 and iType.isDeleted = false")
    Optional<InRoomAmenityType> findByInRoomAmenityTypeIdAndIsDeletedFalse(Long id);

    @Query("select i from InRoomAmenityType i where upper(i.inRoomAmenityTypeName) = upper(?1) and i.isDeleted = false")
    Optional<InRoomAmenityType> findByInRoomAmenityTypeNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}
