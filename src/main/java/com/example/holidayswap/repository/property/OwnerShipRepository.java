package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Ownership;
import com.example.holidayswap.domain.entity.property.OwnershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OwnerShipRepository extends JpaRepository<Ownership, OwnershipId> {

}
