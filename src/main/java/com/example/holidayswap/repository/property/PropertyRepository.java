package com.example.holidayswap.repository.property;

import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
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
            inner join CoOwner o on p.propertyTypeId = o.id.propertyId
            inner join TimeFrame v on v.propertyId = p.id
            inner join AvailableTime tod on tod.timeFrameId = v.id
            where p.resortId = :resortId
            and p.isDeleted = false
            and (:propertyStatus is null  or p.status = :propertyStatus)
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
            + p.numberTwinBeds * 2) >= :numberGuests
            and ((cast(:timeCheckIn as date ) is null or cast(:timeCheckOut as date) is null )
            or ( (tod.startTime BETWEEN :timeCheckIn AND :timeCheckOut)
                 OR
                 (tod.endTime BETWEEN :timeCheckIn AND :timeCheckOut)
                 OR
                 (tod.startTime < :timeCheckIn AND tod.endTime > :timeCheckOut)
                 OR
                 (tod.endTime > :timeCheckIn AND tod.endTime < :timeCheckOut)
                 )
            )
            """)
    Page<Property> findAllByResortIdAndIsDeleteIsFalseIncludeCheckInCheckOut(@Param("resortId") Long resortId,
                                                                             @Param("timeCheckIn") Date timeCheckIn,
                                                                             @Param("timeCheckOut") Date timeCheckOut,
                                                                             @Param("numberGuests") int numberGuests,
                                                                             @Param("propertyStatus") PropertyStatus propertyStatus,
                                                                             Pageable pageable);

    @Query("select p from Property p where p.id = ?1 and p.isDeleted = false ")
    Optional<Property> findPropertyByIdAndIsDeletedIsFalse(Long propertyId);
}
