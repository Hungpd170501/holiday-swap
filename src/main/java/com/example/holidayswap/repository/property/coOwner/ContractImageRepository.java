package com.example.holidayswap.repository.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.ContractImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractImageRepository extends JpaRepository<ContractImage, CoOwnerId> {
    @Query("select c from ContractImage c where c.propertyId = ?1 and c.userId = ?2 and c.roomId = ?3 and c.isDeleted = false")
    List<ContractImage> findAllByPropertyIdAndUserIdAndRoomIdAndIsDeletedIsFalse(Long propertyId, Long UserId, String roomId);

    @Query("select c from ContractImage c where c.id = ?1 and c.isDeleted = false")
    Optional<ContractImage> findByIdAndIsDeletedFalse(Long id);
}
