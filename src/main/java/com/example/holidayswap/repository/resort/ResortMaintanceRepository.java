package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.ResortMaintance;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ResortMaintanceRepository extends JpaRepository<ResortMaintance, Long> {

    @Query("select r from ResortMaintance r where r.type = ?1 AND r.resort.id = ?2")
    List<ResortMaintance> findAllByTypeAndResortId(ResortStatus resortStatus, Long resortId);

    @Query(value = """
            select r.* from resort_maintaince r
            where r.resort_id = ?1 and r.type = ?4 and (( date (?2) > date (r.start_date) AND date (?2) < date (r.end_date))
                            OR (date (?3) > date (r.start_date) AND date (?3) < date (r.end_date))
                            OR( date  (?2) <= date (r.start_date) AND date (?3) >= date (r.end_date) ))
                         """, nativeQuery = true)
    ResortMaintance findByResortIdAndStartDateAndEndDateAndType(Long resortId, LocalDateTime startDate, LocalDateTime endDate, String resortStatus);

    @Query(value = """
            select r.* from resort_maintaince r
            where r.resort_id = ?1 and (( date (?2) >= date (r.start_date) AND date (?2) <= date (r.end_date)))
                         """, nativeQuery = true)
    ResortMaintance findByResortIdAndStartDate(Long resortId, LocalDateTime startDate);

    @Query("select r from ResortMaintance r where r.resortId = ?1 and r.type = ?2")
    ResortMaintance findByResortIdAndType(Long resortId, ResortStatus resortStatus);

    @Query("select r from ResortMaintance r where r.resortId = ?1")
    List<ResortMaintance> findAllByResortId(Long resortId);

    @Query(value = "select r.* from resort_maintaince r where r.type = ?1 and date (r.start_date) <= date (?2)", nativeQuery = true)
    List<ResortMaintance> findByTypeAndStartDate(String type, LocalDateTime startDate);

    @Query(value = """
            SELECT at2.available_time_id
            FROM resort_maintaince rm
                     INNER JOIN resort r ON r.resort_id = rm.resort_id
                     INNER JOIN property p ON p.resort_id = r.resort_id
                     INNER JOIN co_owner co ON co.property_id = p.property_id
                     INNER JOIN available_time at2 ON at2.co_owner_id = co.co_owner_id
            WHERE CASE
                      WHEN rm."type" = 'MAINTENANCE'
                          THEN
                          (date(rm.start_date) <= date(at2.start_time)) AND
                          (date(rm.end_date) >= date(at2.end_time))
                      ELSE
                          (
                              date(rm.start_date) <= date(at2.start_time)
                              )
                      END
            group by at2.available_time_id
            """, nativeQuery = true)
    Set<Long> findCanNotUse();
}
