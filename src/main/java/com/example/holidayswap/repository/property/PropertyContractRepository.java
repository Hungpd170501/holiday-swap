package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyContractRepository extends JpaRepository<Ownership, Long> {
//    @Query("select p from Ownership p where p.propertyId = ?1 and p.isDeleted = false")
//    List<Ownership> findAllByPropertyIdAndIsDeletedIsFalse(Long propertyId);
//
//    @Query("select p from Ownership p where p.id = ?1 and p.isDeleted = false")
//    Optional<Ownership> findByIdAndIsDeletedIsFalse(Long id);
}
