package com.example.holidayswap.repository.property.inRoomAmenity;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InRoomAmenityRepository extends JpaRepository<InRoomAmenity, Long> {
    Page<InRoomAmenity> findInRoomAmenitiesByInRoomAmenitiesNameContainingAndAndIsDeletedIsFalse(String name, Pageable pageable);

    Optional<InRoomAmenity> findByIdAndIsDeletedIsFalse(Long id);
}
