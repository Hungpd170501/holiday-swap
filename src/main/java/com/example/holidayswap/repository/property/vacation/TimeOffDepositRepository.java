package com.example.holidayswap.repository.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.TimeOffDeposit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeOffDepositRepository extends JpaRepository<TimeOffDeposit, Long> {
    @Query("select t from TimeOffDeposit t where t.vacationId = ?1 and t.isDeleted = false")
    Page<TimeOffDeposit> findAllByVacationIdAndDeletedIsFalse(Long vacationId, Pageable pageable);

    @Query("select t from TimeOffDeposit t where t.id = ?1 and t.isDeleted = false")
    Optional<TimeOffDeposit> findByIdAndDeletedFalse(Long id);

    @Query("""
            select t from TimeOffDeposit t
            where t.vacationId = ?1 and t.isDeleted = false and t.startTime >= ?2 and t.endTime <= ?3""")
    List<TimeOffDeposit> findAllByVacationIdAndAndDeletedFalseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long vacationId, Date startTime, Date endTime);
}
