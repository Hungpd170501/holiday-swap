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
           "where r.id = ?1 " +
           "and p.isDeleted = false " +
           "and pt.isDeleted = false " +
           "and r.isDeleted = false ")
    Page<Property> findAllByResortIdAndIsDeleteIsFalse(Long resortId, Pageable pageable);

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
                 (tod.startTime BETWEEN ?2 AND ?3)
                 OR
                 (tod.endTime BETWEEN ?2 AND ?3)
                 OR
                 (tod.startTime < ?2 AND tod.endTime > ?3)
                 OR
                 (tod.endTime > ?2 AND tod.endTime < ?3)
                 ))
            """)
    Page<Property> findAllByResortIdAndIsDeleteIsFalseIncludeCheckInCheckOut(Long resortId,
                                                                             Date timeCheckIn,
                                                                             Date timeCheckOut,
                                                                             int numberGuests,
                                                                             Pageable pageable);

    @Query(value = """
            select distinct p from Property p
            inner join PropertyType pt on p.propertyTypeId = pt.id
            inner join pt.resorts r
            inner join  Ownership o on p.propertyTypeId = o.id.propertyId
            inner join VacationUnit v on v.propertyId = p.id
            inner join TimeOffDeposit tod on tod.vacationUnitId = v.id
            inner join p.inRoomAmenities ira
            where p.resortId = ?1
            and p.isDeleted = false
            and pt.isDeleted = false
            and r.isDeleted = false
            and o.isDeleted = false
            and v.isDeleted = false
            and (p.numberKingBeds * 2
            + p.numberQueenBeds * 2
            + p.numberSingleBeds
            + p.numberDoubleBeds * 2
            + p.numberFullBeds * 2
            + p.numberMurphyBeds
            + p.numberSofaBeds
            + p.numberTwinBeds * 2) >= ?2
            and ira in ?3
            and tod.isDeleted = false""")
    Page<Property> findAllByResortIdAndIsDeleteIsFalseIncludeCheckInCheckOut(Long resortId,
                                                                             int numberGuests,
                                                                             List<Long> listOfInRoomAmenity,
                                                                             Pageable pageable);

    @Query("select p from Property p where p.id = ?1 and p.isDeleted = false ")
    Optional<Property> findPropertyByIdAndIsDeletedIsFalse(Long propertyId);
}
