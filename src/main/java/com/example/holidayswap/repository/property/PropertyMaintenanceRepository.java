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
            SELECT at2.available_time_id
             FROM property_maintaince pm
                      INNER JOIN property p ON p.property_id = pm.property_id
                      INNER JOIN co_owner co ON co.property_id = p.property_id
                      INNER JOIN available_time at2 ON at2.co_owner_id = co.co_owner_id
             WHERE CASE
                       WHEN pm."type" = 'MAINTENANCE'
                           THEN
                           (date(pm.start_date) <= date(at2.start_time)) AND
                           (date(pm.end_date) >= date(at2.end_time))
                       ELSE
                           (
                               date(pm.start_date) <= date(at2.start_time)
                               )
                       END
             group by at2.available_time_id;
             """, nativeQuery = true)
    Set<Long> findCanNotUse();
}
