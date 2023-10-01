package com.example.holidayswap.repository.property.vacation;

import com.example.holidayswap.domain.entity.property.vacation.VacationStatus;
import com.example.holidayswap.domain.entity.property.vacation.VacationUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Page<VacationUnit> findAllByResortId(Long resostId, Pageable pageable);

    @Query("select v from VacationUnit v where v.id = ?1 and v.isDeleted = false")
    Optional<VacationUnit> findByIdAndIsDeletedIsFalse(Long id);

    @Query("select v from VacationUnit v where v.ownership.property.id = ?1 and v.isDeleted = false")
    Page<VacationUnit> findAllByPropertyIdAndDeletedIsFalse(Long propertyId, Pageable pageable);

    @Query("select v from VacationUnit v where v.id = ?1 and v.isDeleted = false")
    Optional<VacationUnit> findByIdAndDeletedFalse(Long id);

    @Query("""
            select v from VacationUnit v
            where v.ownership.property.id = ?1 and v.isDeleted = false and v.startTime >= ?2 and v.endTime <= ?3""")
    List<VacationUnit> findAllByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long propertyId, Date startTime, Date endTime);

    @Query("""
            select v from VacationUnit v
            where v.ownership.property.id = ?1 and v.isDeleted = false and v.startTime >= ?2 and v.endTime <= ?3""")
    Optional<VacationUnit> findByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Long propertyId, Date startTime, Date endTime);

//    List<VacationUnit> findAllByPropertyIdAndUserIdAndRoomId(Long propertyId, Long userId, String roomId);

//    VacationUnit findByPropertyIdAndDeletedIsFalseAndRoomId(Long propertyId);

    @Query("""
            select v from VacationUnit v
            where v.propertyId = ?1
            and v.roomId = ?2 
            and v.startTime between ?3 and ?4 
            or v.endTime between ?3 and ?4 
            and v.isDeleted = false and v.status = ?5""")
    Optional<VacationUnit> findByPropertyIdAndRoomIdAndStartTimeBetweenAndEndTimeBetweenAndDeletedIsFalseAndStatus(
            Long propertyId,
            Long roomId,
            Date startTime,
            Date endTime,
            VacationStatus vacationUnitStatus
    );
}
