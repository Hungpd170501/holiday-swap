package com.example.holidayswap.repository.property.amenity;

import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
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
            where upper(i.inRoomAmenityName) like upper(concat('%', ?1, '%'))
            and i.isDeleted = false""")
    Page<InRoomAmenity> findAllByInRoomAmenitiesName(String name, Pageable pageable);

    @Query("""
            select i from InRoomAmenity i
            where i.inRoomAmenityTypeId = ?1
            and i.isDeleted = false""")
    Page<InRoomAmenity> findAllByInRoomAmenityTypeId(Long inRoomAmenityTypeId, Pageable pageable);

    @Query("""
            select i from InRoomAmenity i
            where i.inRoomAmenityTypeId = ?1
            and i.isDeleted = false""")
    List<InRoomAmenity> findAllByInRoomAmenityTypeId(Long inRoomAmenityTypeId);

    @Query("""
            select i from InRoomAmenity i
            join  i.properties p
            join i.inRoomAmenityType  iType
            where i.inRoomAmenityTypeId = ?1
            and p.id = ?2
            and i.isDeleted = false""")
    List<InRoomAmenity> findAllByInRoomAmenityTypeIdAndResortId(Long inRoomAmenityTypeId, Long resortId);

    @Query("""
            select i from InRoomAmenity i
            where i.id = ?1
            and i.isDeleted = false""")
    Optional<InRoomAmenity> findByInRoomAmenityTypeIdIdAndIsDeletedFalse(Long inRoomAmenityTypeId);

    @Query("select i from InRoomAmenity i where upper(i.inRoomAmenityName) = upper(?1) and i.isDeleted = false")
    Optional<InRoomAmenity> findByInRoomAmenityNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}
