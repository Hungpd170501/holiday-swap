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

    @Query(value = """
            SELECT VU.VACATION_UNIT_ID,
                   VU.END_TIME,
                   VU.IS_DELETED,
                   VU.START_TIME,
                   VU.STATUS,
                   VU.PROPERTY_ID,
                   VU.USER_ID,
                   VU.ROOM_ID
            FROM VACATION_UNIT VU
                     JOIN OWNERSHIP O ON VU.PROPERTY_ID = O.PROPERTY_ID AND VU.ROOM_ID = O.ROOM_ID AND VU.USER_ID = O.USER_ID
            WHERE VU.PROPERTY_ID = ?1
                AND VU.ROOM_ID = ?2
                AND VU.IS_DELETED = FALSE
                AND (?5 IS NULL OR VU.STATUS = ?5)
                AND (?6 IS NULL OR O.STATUS = ?6)
                AND ((CASE
                          WHEN EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 4 = 0 AND
                               (EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 100 != 0 OR
                                EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 400 = 0)
                              THEN EXTRACT(DOY FROM DATE(VU.START_TIME))
                          ELSE
                              (CASE
                                   WHEN EXTRACT(MONTH FROM DATE(VU.START_TIME)) > 2
                                       THEN EXTRACT(DOY FROM DATE(VU.START_TIME)) + 1
                                   ELSE EXTRACT(DOY FROM DATE(VU.START_TIME))
                                  END)
                    END) BETWEEN (CASE
                                      WHEN EXTRACT(YEAR FROM DATE(?3)) % 4 = 0 AND
                                           (EXTRACT(YEAR FROM DATE(?3)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?3)) % 400 = 0)
                                          THEN EXTRACT(DOY FROM DATE(?3))
                                      ELSE
                                          (CASE
                                               WHEN EXTRACT(MONTH FROM DATE(?3)) > 2
                                                   THEN EXTRACT(DOY FROM DATE(?3)) + 1
                                               ELSE EXTRACT(DOY FROM DATE(?3))
                                              END)
                    END) AND (CASE
                                  WHEN EXTRACT(YEAR FROM DATE(?3)) < EXTRACT(YEAR FROM DATE(?4))
                                      THEN
                                      (CASE
                                           WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                                (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                               THEN EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                           ELSE
                                               (CASE
                                                    WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                        THEN EXTRACT(DOY FROM DATE(?4)) + 1 + EXTRACT(DOY FROM DATE(?3))
                                                    ELSE EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                                   END)
                                          END)
                                  ELSE
                                      (CASE
                                           WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                                (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                               THEN EXTRACT(DOY FROM DATE(?4))
                                           ELSE
                                               (CASE
                                                    WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                        THEN EXTRACT(DOY FROM DATE(?4)) + 1
                                                    ELSE EXTRACT(DOY FROM DATE(?4))
                                                   END)
                                          END)
                    END))
               OR ((CASE
                        WHEN EXTRACT(YEAR FROM DATE(VU.START_TIME)) < EXTRACT(YEAR FROM DATE(VU.END_TIME))
                            THEN
                            (CASE
                                 WHEN EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 4 = 0 AND
                                      (EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 100 != 0 OR
                                       EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 400 = 0)
                                     THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                 ELSE
                                     (CASE
                                          WHEN EXTRACT(MONTH FROM DATE(VU.END_TIME)) > 2
                                              THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + 1 + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                          ELSE EXTRACT(DOY FROM DATE(VU.END_TIME)) + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                         END)
                                END)
                        ELSE
                            (CASE
                                 WHEN EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 4 = 0 AND
                                      (EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 100 != 0 OR
                                       EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 400 = 0)
                                     THEN EXTRACT(DOY FROM DATE(VU.END_TIME))
                                 ELSE
                                     (CASE
                                          WHEN EXTRACT(MONTH FROM DATE(VU.END_TIME)) > 2
                                              THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + 1
                                          ELSE EXTRACT(DOY FROM DATE(VU.END_TIME))
                                         END)
                                END)
                END) BETWEEN (CASE
                                  WHEN EXTRACT(YEAR FROM DATE(?3)) % 4 = 0 AND
                                       (EXTRACT(YEAR FROM DATE(?3)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?3)) % 400 = 0)
                                      THEN EXTRACT(DOY FROM DATE(?3))
                                  ELSE
                                      (CASE
                                           WHEN EXTRACT(MONTH FROM DATE(?3)) > 2
                                               THEN EXTRACT(DOY FROM DATE(?3)) + 1
                                           ELSE EXTRACT(DOY FROM DATE(?3))
                                          END)
                END) AND (CASE
                              WHEN EXTRACT(YEAR FROM DATE(?3)) < EXTRACT(YEAR FROM DATE(?4))
                                  THEN
                                  (CASE
                                       WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                            (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                           THEN EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                       ELSE
                                           (CASE
                                                WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                    THEN EXTRACT(DOY FROM DATE(?4)) + 1 + EXTRACT(DOY FROM DATE(?3))
                                                ELSE EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                               END)
                                      END)
                              ELSE
                                  (CASE
                                       WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                            (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                           THEN EXTRACT(DOY FROM DATE(?4))
                                       ELSE
                                           (CASE
                                                WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                    THEN EXTRACT(DOY FROM DATE(?4)) + 1
                                                ELSE EXTRACT(DOY FROM DATE(?4))
                                               END)
                                      END)
                END))
               OR ((CASE
                        WHEN EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 4 = 0 AND
                             (EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 400 = 0)
                            THEN EXTRACT(DOY FROM DATE(VU.START_TIME))
                        ELSE
                            (CASE
                                 WHEN EXTRACT(MONTH FROM DATE(VU.START_TIME)) > 2
                                     THEN EXTRACT(DOY FROM DATE(VU.START_TIME)) + 1
                                 ELSE EXTRACT(DOY FROM DATE(VU.START_TIME))
                                END)
                END) < (CASE
                            WHEN EXTRACT(YEAR FROM DATE(?3)) % 4 = 0 AND
                                 (EXTRACT(YEAR FROM DATE(?3)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?3)) % 400 = 0)
                                THEN EXTRACT(DOY FROM DATE(?3))
                            ELSE
                                (CASE
                                     WHEN EXTRACT(MONTH FROM DATE(?3)) > 2
                                         THEN EXTRACT(DOY FROM DATE(?3)) + 1
                                     ELSE EXTRACT(DOY FROM DATE(?3))
                                    END)
                END) AND (CASE
                              WHEN EXTRACT(YEAR FROM DATE(VU.START_TIME)) < EXTRACT(YEAR FROM DATE(VU.END_TIME))
                                  THEN
                                  (CASE
                                       WHEN EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 4 = 0 AND
                                            (EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 100 != 0 OR
                                             EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 400 = 0)
                                           THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                       ELSE
                                           (CASE
                                                WHEN EXTRACT(MONTH FROM DATE(VU.END_TIME)) > 2
                                                    THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + 1 +
                                                         EXTRACT(DOY FROM DATE(VU.START_TIME))
                                                ELSE EXTRACT(DOY FROM DATE(VU.END_TIME)) + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                               END)
                                      END)
                              ELSE
                                  (CASE
                                       WHEN EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 4 = 0 AND
                                            (EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 100 != 0 OR
                                             EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 400 = 0)
                                           THEN EXTRACT(DOY FROM DATE(VU.END_TIME))
                                       ELSE
                                           (CASE
                                                WHEN EXTRACT(MONTH FROM DATE(VU.END_TIME)) > 2
                                                    THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + 1
                                                ELSE EXTRACT(DOY FROM DATE(VU.END_TIME))
                                               END)
                                      END)
                END) > (CASE
                            WHEN EXTRACT(YEAR FROM DATE(?3)) < EXTRACT(YEAR FROM DATE(?4))
                                THEN
                                (CASE
                                     WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                          (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                         THEN EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                     ELSE
                                         (CASE
                                              WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                  THEN EXTRACT(DOY FROM DATE(?4)) + 1 + EXTRACT(DOY FROM DATE(?3))
                                              ELSE EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                             END)
                                    END)
                            ELSE
                                (CASE
                                     WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                          (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                         THEN EXTRACT(DOY FROM DATE(?4))
                                     ELSE
                                         (CASE
                                              WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                  THEN EXTRACT(DOY FROM DATE(?4)) + 1
                                              ELSE EXTRACT(DOY FROM DATE(?4))
                                             END)
                                    END)
                END))
               OR ((CASE
                        WHEN EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 4 = 0 AND
                             (EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(VU.START_TIME)) % 400 = 0)
                            THEN EXTRACT(DOY FROM DATE(VU.START_TIME))
                        ELSE
                            (CASE
                                 WHEN EXTRACT(MONTH FROM DATE(VU.START_TIME)) > 2
                                     THEN EXTRACT(DOY FROM DATE(VU.START_TIME)) + 1
                                 ELSE EXTRACT(DOY FROM DATE(VU.START_TIME))
                                END)
                END) > (CASE
                            WHEN EXTRACT(YEAR FROM DATE(?3)) % 4 = 0 AND
                                 (EXTRACT(YEAR FROM DATE(?3)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?3)) % 400 = 0)
                                THEN EXTRACT(DOY FROM DATE(?3))
                            ELSE
                                (CASE
                                     WHEN EXTRACT(MONTH FROM DATE(?3)) > 2
                                         THEN EXTRACT(DOY FROM DATE(?3)) + 1
                                     ELSE EXTRACT(DOY FROM DATE(?3))
                                    END)
                END) AND (CASE
                              WHEN EXTRACT(YEAR FROM DATE(VU.START_TIME)) < EXTRACT(YEAR FROM DATE(VU.END_TIME))
                                  THEN
                                  (CASE
                                       WHEN EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 4 = 0 AND
                                            (EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 100 != 0 OR
                                             EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 400 = 0)
                                           THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                       ELSE
                                           (CASE
                                                WHEN EXTRACT(MONTH FROM DATE(VU.END_TIME)) > 2
                                                    THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + 1 +
                                                         EXTRACT(DOY FROM DATE(VU.START_TIME))
                                                ELSE EXTRACT(DOY FROM DATE(VU.END_TIME)) + EXTRACT(DOY FROM DATE(VU.START_TIME))
                                               END)
                                      END)
                              ELSE
                                  (CASE
                                       WHEN EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 4 = 0 AND
                                            (EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 100 != 0 OR
                                             EXTRACT(YEAR FROM DATE(VU.END_TIME)) % 400 = 0)
                                           THEN EXTRACT(DOY FROM DATE(VU.END_TIME))
                                       ELSE
                                           (CASE
                                                WHEN EXTRACT(MONTH FROM DATE(VU.END_TIME)) > 2
                                                    THEN EXTRACT(DOY FROM DATE(VU.END_TIME)) + 1
                                                ELSE EXTRACT(DOY FROM DATE(VU.END_TIME))
                                               END)
                                      END)
                END) < (CASE
                            WHEN EXTRACT(YEAR FROM DATE(?3)) < EXTRACT(YEAR FROM DATE(?4))
                                THEN
                                (CASE
                                     WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                          (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                         THEN EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                     ELSE
                                         (CASE
                                              WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                  THEN EXTRACT(DOY FROM DATE(?4)) + 1 + EXTRACT(DOY FROM DATE(?3))
                                              ELSE EXTRACT(DOY FROM DATE(?4)) + EXTRACT(DOY FROM DATE(?3))
                                             END)
                                    END)
                            ELSE
                                (CASE
                                     WHEN EXTRACT(YEAR FROM DATE(?4)) % 4 = 0 AND
                                          (EXTRACT(YEAR FROM DATE(?4)) % 100 != 0 OR EXTRACT(YEAR FROM DATE(?4)) % 400 = 0)
                                         THEN EXTRACT(DOY FROM DATE(?4))
                                     ELSE
                                         (CASE
                                              WHEN EXTRACT(MONTH FROM DATE(?4)) > 2
                                                  THEN EXTRACT(DOY FROM DATE(?4)) + 1
                                              ELSE EXTRACT(DOY FROM DATE(?4))
                                             END)
                                    END)
                END))
                """, nativeQuery = true)
    List<VacationUnit> findByPropertyIdAndRoomIdAndStartTimeBetweenAndEndTimeBetweenAndDeletedIsFalseAndStatus(
            @Param("propertyId") Long propertyId,
            @Param("roomId") String roomId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("statusVacation") String statusVacation,
            @Param("statusOwnership") String statusOwnership
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
