package com.example.holidayswap.repository.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.TimeOffDeposit;
import com.example.holidayswap.domain.entity.property.vacation.TimeOffDepositStatus;
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
public interface TimeOffDepositRepository extends JpaRepository<TimeOffDeposit, Long> {
    @Query("select t from TimeOffDeposit t where t.vacationUnitId = ?1 and t.isDeleted = false")
    Page<TimeOffDeposit> findAllByVacationUnitIdAndDeletedIsFalse(Long vacationId, Pageable pageable);

    @Query("""
            select t from TimeOffDeposit t
            join t.vacation v
            join v.ownership ow
            join ow.property p
            join  p.propertyType pt
            join pt.resorts r
            where r.id = :resortId and t.isDeleted = false""")
    Page<TimeOffDeposit> findAllByResortIdAndDeletedFalse(@Param("resortId") Long resortId, Pageable pageable);

    @Query("""
            select t from TimeOffDeposit t
            join t.vacation v
            join v.ownership ow
            join ow.property p
            where p.id = :propertyId and t.isDeleted = false""")
    Page<TimeOffDeposit> findAllByPropertyIdAndDeletedFalse(@Param("propertyId") Long propertyId, Pageable pageable);

    @Query("select t from TimeOffDeposit t where t.id = ?1 and t.isDeleted = false")
    Optional<TimeOffDeposit> findByIdAndDeletedFalse(Long id);

    @Query("""
            select t from TimeOffDeposit t
            where t.vacationUnitId = ?1 and t.isDeleted = false and t.startTime >= ?2 and t.endTime <= ?3""")
    List<TimeOffDeposit> findAllByVacationIdAndAndDeletedFalseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long vacationId, Date startTime, Date endTime);

    @Query("""
            select t from TimeOffDeposit t
            where t.vacationUnitId = ?1
            and (t.startTime between ?2 and ?3 or t.endTime between ?2 and ?3)
            and t.isDeleted = false and t.status = ?4""")
    Optional<TimeOffDeposit> findDuplicateWhichAnyTimeDeposit(
            Long vacationUnitId,
            Date startTime,
            Date endTime,
            TimeOffDepositStatus timeOffDepositStatus
    );
}
