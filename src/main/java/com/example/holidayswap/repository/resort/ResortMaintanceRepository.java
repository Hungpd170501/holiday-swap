package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.ResortMaintance;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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
}
