package com.example.holidayswap.repository.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.ContractImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractImageRepository extends JpaRepository<ContractImage, Long> {
    @Query("select c from ContractImage c where c.id = ?1 and c.isDeleted = false")
    Optional<ContractImage> findByIdAndIsDeletedFalse(Long id);

    @Query("select c from ContractImage c where c.coOwnerId = :coOwnerId and c.isDeleted = false")
    List<ContractImage> findAllByCoOwnerIdAndIsDeletedIsFalse(@Param("coOwnerId") Long coOwnerId);
}
