package com.example.holidayswap.repository.property.ownership;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface OwnerShipRepository extends JpaRepository<Ownership, OwnershipId> {
    //    @Query("select o from Ownership o where o.property.id = ?1 and o.user.userId = ?2 and o.isDeleted = false ")
//    @Query("select o from Ownership o where o.property.id = ?1 and o.isDeleted = false")
//    List<Ownership> findAllByPropertyIdAndIsDeletedIsFalse(Long propertyId);
//
//    @Query("select o from Ownership o where o.user.userId = ?1 and o.isDeleted = false")
//    List<Ownership> findAllByUserIdAndIsDeletedIsFalse(Long userId);
//
//    @Query("select o from Ownership o where o.property.id = ?1 and o.user.userId = ?2 and o.isDeleted = false ")
//    Optional<Ownership> findAllByPropertyIdAndUserIdAndIsDeleteIsFalse(Long propertyId, Long userId);
//
//    List<Ownership> findAllByPropertyIdAndIsDeletedIsFalseAndStatusAndRoomId(Long propertyId, ContractStatus status, String roomId);
    Ownership findByIdAndStatus(OwnershipId id, ContractStatus status);
}
