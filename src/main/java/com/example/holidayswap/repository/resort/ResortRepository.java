package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.Resort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {
    @Query("select r from Resort r where upper(r.resortName) like upper(concat('%', ?1, '%')) and r.isDeleted = false")
    Page<Resort> findAllByResortNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);

    @Query("""
            select DISTINCT r  from Resort r
            inner join r.propertyTypes pt
            inner join Property p on p.resortId = r.id
            inner join Ownership o on o.id.propertyId = p.id
            inner join VacationUnit vu on vu.propertyId = p.id
                        
            inner join TimeOffDeposit tod on tod.vacationUnitId = vu.id
            where upper(r.resortName) like upper(concat('%', ?1, '%'))
            and r.isDeleted = false
            and p.isDeleted = false
            and o.isDeleted = false
            and vu.isDeleted = false
            and tod.isDeleted = false
            and (p.numberKingBeds * 2
            + p.numberQueenBeds * 2
            + p.numberSingleBeds
            + p.numberDoubleBeds * 2
            + p.numberFullBeds * 2
            + p.numberMurphyBeds
            + p.numberSofaBeds
            + p.numberTwinBeds * 2) >= ?4
            and ((?2 is null or ?3 is null ) or (tod.startTime between ?2 AND ?3 or tod.endTime  between ?2 AND ?3))
            """)
    Page<Resort> findAllByFilter(
            String name,
            Date startDate,
            Date endDate,
            int numberGuests,
            Pageable pageable
    );

    @Query("""
            select DISTINCT r  from Resort r
            inner join r.propertyTypes pt
            inner join Property p on p.resortId = r.id
            inner join Ownership o on o.id.propertyId = p.id
            inner join VacationUnit vu on vu.propertyId = p.id
                       
            inner join TimeOffDeposit tod on tod.vacationUnitId = vu.id
            where upper(r.resortName) like upper(concat('%', ?1, '%'))
            and r.isDeleted = false
            and p.isDeleted = false
            and o.isDeleted = false
            and vu.isDeleted = false
            and (p.numberKingBeds * 2
            + p.numberQueenBeds * 2
            + p.numberSingleBeds
            + p.numberDoubleBeds * 2
            + p.numberFullBeds * 2
            + p.numberMurphyBeds
            + p.numberSofaBeds
            + p.numberTwinBeds * 2) >= ?2
            and tod.isDeleted = false""")
    Page<Resort> findAllByFilter(
            String name,
            int numberGuests,
            Pageable pageable
    );

    @Query("select r from Resort r where r.id = ?1 and r.isDeleted = false")
    Optional<Resort> findByIdAndDeletedFalse(Long id);

    @Query("select r from Resort r where upper(r.resortName) like upper(concat('%', ?1, '%')) and r.isDeleted = false")
    Optional<Resort> findByResortNameContainingIgnoreCaseAndDeletedFalse(String name);

    @Query("select r from Resort r where upper(r.resortName) = upper(?1) and r.isDeleted = false")
    Optional<Resort> findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}
