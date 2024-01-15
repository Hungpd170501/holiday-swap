package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.PropertyMaintenance;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PropertyMaintenanceRepository extends JpaRepository<PropertyMaintenance, Long> {
    @Query("select r from PropertyMaintenance r where r.type = ?1 AND r.property.id = ?2")
    List<PropertyMaintenance> findAllByTypeAndProperty(PropertyStatus resortStatus, Long resortId);

    @Query(value = """
            select r.* from property_maintaince r
            where r.property_id = ?1 and r.type = ?4 and (( date (?2) > date (r.start_date) AND date (?2) < date (r.end_date))
                            OR (date (?3) > date (r.start_date) AND date (?3) < date (r.end_date))
                            OR( date  (?2) <= date (r.start_date) AND date (?3) >= date (r.end_date) ))
                         """, nativeQuery = true)
    PropertyMaintenance findByPropertyIdAndStartDateAndEndDateAndType(Long propertyId, LocalDateTime startDate, LocalDateTime endDate, String resortStatus);

    @Query(value = "select r.* from property_maintaince r where r.type = ?1 and date (r.start_date) = date (?2)", nativeQuery = true)
    List<PropertyMaintenance> findByTypeAndStartDate(String type, LocalDateTime startDate);

    @Query(value = """
            select pm.property_id from property_maintaince pm where current_date between pm.start_date and pm.end_date
             """, nativeQuery = true)
    Set<Long> findCanNotUse();
}
