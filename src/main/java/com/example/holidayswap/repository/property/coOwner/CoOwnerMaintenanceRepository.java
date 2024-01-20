package com.example.holidayswap.repository.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import com.example.holidayswap.domain.entity.property.coOwner.OwnerShipMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoOwnerMaintenanceRepository extends JpaRepository<OwnerShipMaintenance,Long> {
    @Query("select o from OwnerShipMaintenance o where o.propertyId = ?1 and o.apartmentId = ?2")
    Optional <OwnerShipMaintenance> findByPropertyIdAndApartmentId(Long propertyId, String apartmentId);
    @Query("select o from OwnerShipMaintenance o where o.propertyId = ?1 and o.apartmentId = ?2 and o.type = ?3")
    List <OwnerShipMaintenance> findByPropertyIdAndApartmentIdAndType(Long propertyId, String apartmentId, CoOwnerMaintenanceStatus type);

    @Query(value = """
            select r.* from owner_ship_maintenance r
            where r.property_id = ?1 and r.type = ?5 and r.apartment_id = ?4 and (( date (?2) > date (r.start_date) AND date (?2) < date (r.end_date))
                            OR (date (?3) > date (r.start_date) AND date (?3) < date (r.end_date))
                            OR( date  (?2) <= date (r.start_date) AND date (?3) >= date (r.end_date) ))
                         """, nativeQuery = true)
    List<OwnerShipMaintenance> findByPropertyIdAndApartmentIdAndStartDateAndEndDateAndType(Long propertyId, LocalDateTime startDate, LocalDateTime endDate, String apartmentId, String type);

    @Query(value = """
            select r.* from owner_ship_maintenance r
            where r.property_id = ?3 and r.type = ?5 and r.apartment_id = ?4 and ((r.start_date BETWEEN date (?2) AND date (?3)) OR r.start_date = date (?2) OR r.start_date = date (?3))
                         """, nativeQuery = true)
    OwnerShipMaintenance findCoOwnerMaintenanceByStartDateAndEndDateAndPropertyIdAndApartmentIdAndType(LocalDateTime startDate, LocalDateTime endDate, Long propertyId, String apartmentId, String type);
}
