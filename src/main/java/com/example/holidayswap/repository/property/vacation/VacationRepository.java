package com.example.holidayswap.repository.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {
    @Query("select v from Vacation v where v.propertyId = ?1 and v.isDeleted = false")
    Page<Vacation> findAllByPropertyIdAndDeletedIsFalse(Long propertyId, Pageable pageable);

    @Query("select v from Vacation v where v.id = ?1 and v.isDeleted = false")
    Optional<Vacation> findByIdAndDeletedFalse(Long id);

    @Query("""
            select v from Vacation v
            where v.propertyId = ?1 and v.isDeleted = false and v.startTime >= ?2 and v.endTime <= ?3""")
    List<Vacation> findAllByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long propertyId, Date startTime, Date endTime);

}
