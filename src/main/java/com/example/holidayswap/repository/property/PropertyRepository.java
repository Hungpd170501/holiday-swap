package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    //    @Query("select p from Property p " +
//            "join p.propertyContracts pC " +
//            "join  pC.contractImages cI " +
//            "where p.id = ?1 and pC.isDeleted = false and cI.isDeleted = false ")
//    Optional<Property> findPropertyByIdAndPropertyContractsIsFalseAndPropertyContractsHaveContractImageIsFalse(Long propertyId);
    Optional<Property> findPropertyById(Long propertyId);
}
