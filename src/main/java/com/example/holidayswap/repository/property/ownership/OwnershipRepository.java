package com.example.holidayswap.repository.property.ownership;

import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.ContractType;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface OwnershipRepository extends JpaRepository<Ownership, OwnershipId> {
    //    @Query("select o from Ownership o where o.property.id = ?1 and o.user.userId = ?2 and o.isDeleted = false ")
    @Query("select o from Ownership o where o.property.id = ?1 and o.isDeleted = false")
    List<Ownership> findAllByPropertyIdAndIsDeletedIsFalse(Long propertyId);

    @Query("select o from Ownership o where o.user.userId = ?1 and o.isDeleted = false")
    List<Ownership> findAllByUserIdAndIsDeletedIsFalse(Long userId);

    @Query("select o from Ownership o where o.property.id = ?1 and o.user.userId = ?2 and o.isDeleted = false ")
    Optional<Ownership> findAllByPropertyIdAndUserIdAndIsDeleteIsFalse(Long propertyId, Long userId);

    @Query("select o from Ownership o where o.id.ownershipId = ?1 and o.isDeleted = false ")
    Optional<Ownership> findById(Long ownershipId);

    @Query("""
            select o from Ownership o
            where o.id.propertyId = ?1
            and o.id.userId = ?2
            and o.id.roomId = ?3
            and o.startTime >= ?4
            and o.endTime <= ?5
            and o.type = ?6
            and o.status = ?7
            and o.isDeleted = false""")
    Optional<Ownership> findByTypeIsRightToUse(
            Long propertyId,
            Long userId,
            Long roomId,
            Date startTime,
            Date endTime,
            ContractType type,
            ContractStatus status);

    @Query("""
            select o from Ownership o
            where o.id.propertyId = ?1
            and o.id.userId = ?2
            and o.id.roomId = ?3
            and o.type = ?4
            and o.status = ?5
            and o.isDeleted = false""")
    Optional<Ownership> findByTypeIsDeeded(
            Long propertyId,
            Long userId,
            Long roomId,
            ContractType type,
            ContractStatus status);
}
