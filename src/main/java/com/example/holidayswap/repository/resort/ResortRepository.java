package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.resort.Resort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {
    //    (
//            (tod.startTime BETWEEN ?2 AND ?3)
//    OR
//            (tod.endTime BETWEEN ?2 AND ?3)
//    OR
//            (tod.startTime < ?2 AND tod.endTime > ?3)
//    OR
//            (tod.endTime > ?2 AND tod.endTime < ?3)
//                 )
    @Query("""
            select DISTINCT r  from Resort r
            inner join r.propertyTypes pt
            inner join r.amenities ra
            inner join Property p on p.resortId = r.id
            inner join p.inRoomAmenities pa
            inner join Ownership o on o.id.propertyId = p.id
            inner join VacationUnit vu on vu.propertyId = p.id
            inner join TimeOffDeposit tod on tod.vacationUnitId = vu.id
            where upper(r.resortName) like upper(concat('%', ?1, '%'))
            and r.isDeleted = false
            and p.isDeleted = false
            and o.isDeleted = false
            and vu.isDeleted = false
            and tod.isDeleted = false
            and ((cast(?2 as date ) is null or cast(?3 as date) is null )
            or  (
             (tod.startTime BETWEEN ?2 AND ?3)
                 OR
                 (tod.endTime BETWEEN ?2 AND ?3)
                 OR
                 (tod.startTime < ?2 AND tod.endTime > ?3)
                 OR
                 (tod.endTime > ?2 AND tod.endTime < ?3)
                 )
             )
            and (p.numberKingBeds * 2
            + p.numberQueenBeds * 2
            + p.numberSingleBeds
            + p.numberDoubleBeds * 2
            + p.numberFullBeds * 2
            + p.numberMurphyBeds
            + p.numberSofaBeds
            + p.numberTwinBeds * 2) >= ?4
            and ((:#{#listOfResortAmenity == null} = true) or (r.id in ?5))
            and ((:#{#listOfInRoomAmenity == null} = true) or (p.id in ?6))
            """)
    Page<Resort> findAllByFilter(
            String name,
            Date startDate,
            Date endDate,
            int numberGuests,
            @Param("listOfResortAmenity") Set<Long> listOfResortAmenity,
            @Param("listOfInRoomAmenity") Set<Long> listOfInRoomAmenity,
            Pageable pageable
    );

    @Query("select r from Resort r where r.id = ?1 and r.isDeleted = false")
    Optional<Resort> findByIdAndDeletedFalse(Long id);
    @Query("select r from Resort r where upper(r.resortName) = upper(?1) and r.isDeleted = false")
    Optional<Resort> findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}
