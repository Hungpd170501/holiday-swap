package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
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
    @Query("select r from Resort r where r.id = :resortId and r.isDeleted = false and r.status = :resortStatus")
    Optional<Resort> findByIdAndDeletedFalseAndResortStatus(@Param(("resortId")) Long id,
                                                            @Param(("resortStatus")) ResortStatus resortStatus);

    @Query("""
            select DISTINCT r  from Resort r
            inner join r.propertyTypes pt
            inner join r.amenities ra
            inner join Property p on p.resortId = r.id
            inner join p.inRoomAmenities pa
            inner join CoOwner o on p.id = o.id.propertyId
            inner join TimeFrame v on v.propertyId = p.id
            inner join AvailableTime at on at.timeFrameId = v.id
            where upper(r.resortName) like upper(concat('%', :name, '%'))
            and r.isDeleted = false and (:resortStatus is null or r.status = :resortStatus)
            and p.isDeleted = false and (:propertyStatus is null or p.status = :propertyStatus)
            and o.isDeleted = false and (:coOwnerStatus is null or o.status = :coOwnerStatus)
            and v.isDeleted = false and (:timeFrameStatus is null or v.status = :timeFrameStatus)
            and at.isDeleted = false and (:availableTimeStatus is null or at.status = :availableTimeStatus)
            and ((cast(:startDate as date ) is null or cast(:endDate as date) is null )
            or (
             (at.startTime BETWEEN :startDate AND :endDate)
                 OR
                 (at.endTime BETWEEN :startDate AND :endDate)
                 OR
                 (at.startTime < :startDate AND at.endTime > :endDate)
                 OR
                 (at.endTime > :startDate AND at.endTime < :endDate)
                 ))
            and (p.numberKingBeds * 2
            + p.numberQueenBeds * 2
            + p.numberSingleBeds
            + p.numberDoubleBeds * 2
            + p.numberFullBeds * 2
            + p.numberMurphyBeds
            + p.numberSofaBeds
            + p.numberTwinBeds * 2) >= :numberGuests
            and ((:#{#listOfResortAmenity == null} = true) or (r.id in :listOfResortAmenity))
            and ((:#{#listOfInRoomAmenity == null} = true) or (p.id in :listOfInRoomAmenity))
            """)
    Page<Resort> findAllByFilter(
            @Param("name") String name,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("numberGuests") int numberGuests,
            @Param("listOfResortAmenity") Set<Long> listOfResortAmenity,
            @Param("listOfInRoomAmenity") Set<Long> listOfInRoomAmenity,
            @Param("resortStatus") ResortStatus resortStatus,
            @Param("propertyStatus") PropertyStatus propertyStatus,
            @Param("coOwnerStatus") CoOwnerStatus coOwnerStatus,
            @Param("timeFrameStatus") TimeFrameStatus timeFrameStatus,
            @Param("availableTimeStatus") AvailableTimeStatus availableTimeStatus,
            Pageable pageable
    );

    @Query("""
            select DISTINCT r  from Resort r
            inner join r.propertyTypes pt
            inner join r.amenities ra
            where upper(r.resortName) like upper(concat('%', :name, '%'))
            and r.isDeleted = false and (:resortStatus is null or r.status = :resortStatus)
            and ((:#{#listOfResortAmenity == null} = true) or (r.id in :listOfResortAmenity))
            """)
    Page<Resort> findAllByFilter(
            @Param("name") String name,
            @Param("listOfResortAmenity") Set<Long> listOfResortAmenity,
            @Param("resortStatus") ResortStatus resortStatus,
            Pageable pageable
    );

    @Query("select r from Resort r where r.id = :resortId and r.isDeleted = false")
    Optional<Resort> findByIdAndDeletedFalse(@Param(("resortId")) Long id);

    @Query("select r from Resort r where upper(r.resortName) = upper(?1) and r.isDeleted = false")
    Optional<Resort> findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(String name);
}
