package com.example.holidayswap.repository.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.VacationUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacationUnitRepository extends JpaRepository<VacationUnit, Long> {
    @Query("select v from VacationUnit v where v.ownership.property.id = ?1 and v.isDeleted = false ")
    Page<VacationUnit> findAllByPropertyId(Long propertyId, Pageable pageable);

    @Query("""
            select v from VacationUnit v
            join v.ownership o
            join o.property p
            join p.propertyType pT
            join pT.resorts s
            where s.id = ?1
            and v.isDeleted = false""")
    Page<VacationUnit> findAllByResortId(Long resortId, Pageable pageable);

    @Query("select v from VacationUnit v where v.id = ?1 and v.isDeleted = false")
    Optional<VacationUnit> findByIdAndIsDeletedIsFalse(Long id);

    @Query("select v from VacationUnit v where v.ownership.property.id = ?1 and v.isDeleted = false")
    Page<VacationUnit> findAllByPropertyIdAndDeletedIsFalse(Long propertyId, Pageable pageable);

    @Query("select v from VacationUnit v where v.id = ?1 and v.isDeleted = false")
    Optional<VacationUnit> findByIdAndDeletedFalse(Long id);

    @Query("""
            select v from VacationUnit v
            where v.ownership.property.id = ?1
            and v.isDeleted = false
            and v.startTime >= ?2
            and v.endTime <= ((CAST(?1 AS date)))""")
    List<VacationUnit> findAllByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long propertyId, Date startTime, Date endTime);

    @Query("""
            select v from VacationUnit v
            where v.ownership.property.id = ?1
            and v.isDeleted = false
            and v.startTime >= ?2
            and v.endTime <= ((CAST(?1 AS date)))""")
    Optional<VacationUnit> findByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long propertyId, Date startTime, Date endTime);

    //    @Query(value = """
//                select vu from vacation_unit vu
//                where ( CASE
//                            WHEN (EXTRACT(YEAR FROM vu.startTime) % 4 = 0) AND (EXTRACT(YEAR FROM vu.startTime) % 100 != 0 OR EXTRACT(YEAR FROM vu.startTime) % 400 = 0)
//                            THEN   DATE(DATE ('400-01-01') + (vu.startTime - date_trunc('year', vu.startTime)))
//                            ELSE   DATE(DATE ('400-01-02') + (vu.startTime - date_trunc('year', vu.startTime)))
//                        END
//                    ,  CASE
//                            WHEN EXTRACT(YEAR FROM vu.endTime) % 4 = 0 AND (EXTRACT(YEAR FROM vu.endTime) % 100 != 0 OR EXTRACT(YEAR FROM vu.endTime) % 400 = 0)
//                            THEN   DATE(DATE ('400-01-01') + (vu.endTime - date_trunc('year', vu.endTime)))
//                            ELSE   DATE(DATE ('400-01-02') + (vu.endTime - date_trunc('year', vu.endTime)))
//                        END)
//                    OVERLAPS (
//                     CASE
//                            WHEN EXTRACT(YEAR FROM DATE (CAST(?1 AS date))) % 4 = 0 AND (EXTRACT(YEAR FROM DATE (CAST(?1 AS date))) % 100 != 0 OR EXTRACT(YEAR FROM DATE ((CAST(?1 AS date)))) % 400 = 0)
//                            THEN DATE(DATE (('400-01-01')) +  (DATE (CAST(?1 AS date)) - date_trunc('year', DATE (CAST(?1 AS date)))))
//                            ELSE DATE(DATE ('400-01-02') +  (DATE (CAST(?1 AS date)) - date_trunc('year', DATE (CAST(?1 AS date)))))
//                        END
//                     , CASE
//                    	 WHEN EXTRACT(YEAR FROM DATE(CAST(?2 AS date))) % 4 = 0 AND (EXTRACT(YEAR FROM DATE (CAST(?2 AS date))) % 100 != 0 OR EXTRACT(YEAR FROM DATE (CAST(?2 AS date))) % 400 = 0)
//                            THEN DATE(DATE ('400-01-01') +  (DATE (CAST(?2 AS date)) - date_trunc('year', DATE (CAST(?2 AS date)))))
//                            ELSE DATE(DATE ('400-01-02') +  (DATE (CAST(?2 AS date)) - date_trunc('year', DATE (CAST(?2 AS date)))))
//                            end
//                    )
//                    """, nativeQuery = true)
//    List<VacationUnit> findByPropertyIdAndRoomIdAndStartTimeBetweenAndEndTimeBetweenAndDeletedIsFalseAndStatus(
//            Long propertyId,
//            String roomId,
//            Date startTime,
//            Date endTime,
//            VacationStatus vacationStatus
//    );
    @Query(value = """
            select *
                from vacation_unit vu
                where vu.property_id = ?1
                  and vu.room_id = ?2
                  and vu.is_deleted = false
                  and (CASE
                           WHEN (EXTRACT(YEAR FROM vu.start_time) % 4 = 0) AND
                                (EXTRACT(YEAR FROM vu.start_time) % 100 != 0 OR EXTRACT(YEAR FROM vu.start_time) % 400 = 0)
                               THEN DATE(DATE '400-01-01' + (vu.start_time - date_trunc('year', vu.start_time)))
                           ELSE DATE(DATE '400-01-02' + (vu.start_time - date_trunc('year', vu.start_time)))
                           END, CASE
                                    WHEN EXTRACT(YEAR FROM vu.end_time) % 4 = 0 AND
                                         (EXTRACT(YEAR FROM vu.end_time) % 100 != 0 OR EXTRACT(YEAR FROM vu.end_time) % 400 = 0)
                                        THEN DATE(DATE '400-01-01' + (vu.end_time - date_trunc('year', vu.end_time)))
                                    ELSE DATE(DATE '400-01-02' + (vu.end_time - date_trunc('year', vu.end_time)))
                           END)
                    OVERLAPS (
                              CASE
                                  WHEN EXTRACT(YEAR FROM DATE(?3)) % 4 = 0 AND
                                       (EXTRACT(YEAR FROM DATE(?3)) % 100 != 0 OR
                                        EXTRACT(YEAR FROM DATE(?3)) % 400 = 0)
                                      THEN DATE(DATE '400-01-01' + (DATE(?3) - date_trunc('year', DATE(?3))))
                                  ELSE DATE(DATE '400-01-02' + (DATE(?3) - date_trunc('year', DATE(?3))))
                                  END, CASE
                                           WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                                (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR
                                                 EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                               THEN DATE(DATE '400-01-01' +
                                                         (DATE(?4) - date_trunc('year', DATE(?4))))
                                           ELSE DATE(DATE '400-01-02' + (DATE(?4) - date_trunc('year', DATE(?4))))
                                  end
                          )
                    and vu.status = ?5
                """, nativeQuery = true)
    List<VacationUnit> findByPropertyIdAndRoomIdAndStartTimeBetweenAndEndTimeBetweenAndDeletedIsFalseAndStatus(
            @Param("propertyId") Long propertyId,
            @Param("roomId") String roomId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("status") String status
    );

//    @Query("""
//            select v from VacationUnit v
//            where v.propertyId = ?1
//            and v.roomId = ?2
//            and v.startTime <= ((CAST(?1 AS date)))
//            and v.endTime >= (CAST(?2 AS date))
//            and v.isDeleted = false and v.status = ?5""")
//    Optional<VacationUnit> findByStartTimeAndEndTimeIsInVacationUnitTime(
//            Long propertyId,
//            String roomId,
//            Date startTime,
//            Date endTime,
//            VacationStatus vacationStatus
//    );
}
