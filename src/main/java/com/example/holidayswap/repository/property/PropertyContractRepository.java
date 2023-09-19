package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyContractRepository extends JpaRepository<PropertyContract, Long> {
    @Query("select p from PropertyContract p where p.propertyId = ?1 and p.isDeleted = false")
    List<PropertyContract> findAllByPropertyIdAndIsDeletedIsFalse(Long propertyId);

    @Query("select p from PropertyContract p where p.id = ?1 and p.isDeleted = false")
    Optional<PropertyContract> findByIdAndIsDeletedIsFalse(Long id);
}
