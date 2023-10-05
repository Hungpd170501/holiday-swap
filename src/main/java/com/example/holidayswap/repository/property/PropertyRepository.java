package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p " +
           "join p.propertyType pt " +
           "join  pt.resorts r " +
           "where p.resortId = ?1 " +
           "and p.isDeleted = false " +
           "and pt.isDeleted = false " +
           "and r.isDeleted = false ")
    List<Property> findAllByResortIdAndIsDeleteIsFalse(Long resortId);

    @Query(value = """
            select distinct p from Property p
            inner join PropertyType pt on p.propertyTypeId = pt.id
            inner join pt.resorts r
            inner join  Ownership o on p.propertyTypeId = o.id.propertyId
            inner join VacationUnit v on v.propertyId = p.id
            inner join TimeOffDeposit tod on tod.vacationUnitId = v.id
            where p.resortId = ?1
            and p.isDeleted = false
            and pt.isDeleted = false
            and r.isDeleted = false
            and o.isDeleted = false
            and v.isDeleted = false
            and tod.isDeleted = false
            and (p.numberKingBeds * 2
            + p.numberQueenBeds * 2
            + p.numberSingleBeds
            + p.numberDoubleBeds * 2
            + p.numberFullBeds * 2
            + p.numberMurphyBeds
            + p.numberSofaBeds
            + p.numberTwinBeds * 2) >= ?4
            and ((cast(?2 as date ) is null or cast(?3 as date) is null )
            or (
            overlaps( cast(tod.startTime as date ),  cast(tod.endTime as date )
            , cast(?2 as date ), cast(?3 as date )) = true))
            """)
    Page<Property> findAllByResortIdAndIsDeleteIsFalseIncludeCheckInCheckOut(Long resortId,
                                                                             Date timeCheckIn,
                                                                             Date timeCheckOut,
                                                                             int numberGuests,
                                                                             Pageable pageable);

    @Query("select p from Property p where p.id = ?1 and p.isDeleted = false ")
    Optional<Property> findPropertyByIdAndIsDeletedIsFalse(Long propertyId);
}
