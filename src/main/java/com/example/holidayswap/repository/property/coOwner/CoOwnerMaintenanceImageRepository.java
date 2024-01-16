package com.example.holidayswap.repository.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.OwnerShipMaintenanceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoOwnerMaintenanceImageRepository extends JpaRepository<OwnerShipMaintenanceImage,Long> {
}
