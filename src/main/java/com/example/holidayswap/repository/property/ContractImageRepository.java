package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.ContractImage;
import com.example.holidayswap.domain.entity.property.OwnershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractImageRepository extends JpaRepository<ContractImage, OwnershipId> {
//    @Query("select c from ContractImage c where  c.isDeleted = false")
////c.ownershipId = ?1 and
//    List<ContractImage> findContractImagesByPropertyContractIdAndIsDeletedIsFalse(Long contractId);
//
//    @Query("select c from ContractImage c where c.id = ?1 and c.isDeleted = false")
//    Optional<ContractImage> findContractImageByIdAndIsDeleteIsFalse(Long id);

}
