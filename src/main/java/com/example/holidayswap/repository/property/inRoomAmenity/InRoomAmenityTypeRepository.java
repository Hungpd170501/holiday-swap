package com.example.holidayswap.repository.property.inRoomAmenity;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenityType;
import io.lettuce.core.dynamic.annotation.Param;
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
            select iType from InRoomAmenityType iType
             join iType.inRoomAmenities i
             join i.propertyInRoomAmenities pI
             join pI.property p
             where p.id = :propertyId and iType.isDeleted = false""")
    List<InRoomAmenityType> findInRoomAmenityTypesByPropertyIdAndDeletedFalse(@Param("propertyId") Long propertyId);

    @Query("""
            select i from InRoomAmenityType i
            where upper(i.inRoomAmenityTypeName) like upper(concat('%', ?1, '%')) and i.isDeleted = false""")
    Page<InRoomAmenityType> findAllByInRoomAmenityTypeNameContainingIgnoreCaseAndIsDeletedIsFalse(String name, Pageable pageable);

    Optional<InRoomAmenityType> findByIdAndIsDeletedIsFalse(Long id);
}
