package com.example.holidayswap.repository.resort;

import com.example.holidayswap.domain.dto.response.property.ResortApartmentForRentDTO;
import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {
    @Query("select r from Resort r where r.id = :resortId and r.isDeleted = false and r.status = :resortStatus")
    Optional<Resort> findByIdAndDeletedFalseAndResortStatus(@Param(("resortId")) Long id, @Param(("resortStatus")) ResortStatus resortStatus);

    @Query("select r from Resort r where r.id = :resortId and r.isDeleted = false ")
    Optional<Resort> findByIdAndIsDeletedIsFalse(@Param(("resortId")) Long id);

    @Query("""
            select DISTINCT r  from Resort r
            left join r.propertyTypes pt
            left join r.amenities ra
            where upper(r.resortName) like upper(concat('%', :name, '%'))
            and r.isDeleted = false and (:resortStatus is null or r.status = :resortStatus)
            and ((:#{#listOfResortAmenity == null} = true) or (ra.id in :listOfResortAmenity))
            AND (:locationName = '' OR unaccent(upper(r.locationFormattedName)) LIKE %:locationName%)
            """)
    Page<Resort> findAllByFilter(@Param("locationName") String locationName, @Param("name") String name, @Param("listOfResortAmenity") Set<Long> listOfResortAmenity, @Param("resortStatus") ResortStatus resortStatus, Pageable pageable);

    @Query("select r from Resort r where r.id = :resortId and r.isDeleted = false")
    Optional<Resort> findByIdAndDeletedFalse(@Param(("resortId")) Long id);

    @Query("select r from Resort r where upper(r.resortName) = upper(?1) and r.isDeleted = false")
    Optional<Resort> findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(String name);

//    @Query(value = """
//            select r from Resort r
//            left  join r.properties p
//            left  join  p.coOwners co
//            where co.user.userId = :userId
//            and r.isDeleted = false
//            and p.isDeleted = false
//            and co.isDeleted = false
//            and co.status = :coOwnerStatus
//            """)
//    Page<Resort> findAllResortHaveUserOwner(@Param("userId") Long userId, @Param("coOwnerStatus") CoOwnerStatus coOwnerStatus, Pageable pageable);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ResortApartmentForRentDTO (
              r
            )
            from Resort r
            left join r.properties p
            left join p.coOwners co
            left join  co.timeFrames tf
            left join  co.availableTimes at
                 left join co.user u
                 left join p.inRoomAmenities ira
                 left join p.propertyType pt
                 left join p.propertyView pv
                 left join at.bookings bk
                 where
              
                   ((:min is null) or ( at.pricePerNight >= cast(:min as double))
                 and ((:max is null) or at.pricePerNight <= cast(:max as double)))
                 and (
                    ((cast(:checkIn as date ) is null) and (cast(:checkOut as date) is null))
                        or ((date(:checkOut) between date(at.startTime) and date(at.endTime))
                        and (date(:checkIn)) between date(at.startTime) and date(at.endTime))
                     )
                 and co.status = 'ACCEPTED'
               
                 and at.status = 'OPEN'
                 and p.status = 'ACTIVE'
                 and r.status = 'ACTIVE'
                 and u.status = 'ACTIVE'
               and ((:#{#listOfInRoomAmenity == null} = true) or (ira.id in :listOfInRoomAmenity))
               and ((:#{#listOfPropertyView == null} = true) or (pv.id in :listOfPropertyView))
               and ((:#{#listOfPropertyType == null} = true) or (pt.id in :listOfPropertyType))
               and (p.numberKingBeds * 2
               + p.numberQueenBeds * 2
               + p. numberSingleBeds
               + p.numberDoubleBeds * 2
               + p.numberTwinBeds * 2
               + p.numberFullBeds * 2
               + p.numberMurphyBeds * 2
               + p.numberSofaBeds * 2) >= :guest
               and p.numberBedsRoom >= :numberBedsRoom
               and p.numberBathRoom >= :numberBathRoom
               AND (:locationName = '' OR unaccent(upper(r.locationFormattedName)) LIKE %:locationName%)
               and (:userId is null or u.userId != :userId)
               and (
                   (extract(day from cast(at.endTime as timestamp )) - extract(day from cast(at.startTime as timestamp )))
                   >
                   (select sum(extract(day from cast(bk.checkOutDate as timestamp )) - extract(day from cast(bk.checkInDate as timestamp ))) from Booking bk where bk.availableTimeId = at.id)
                   or bk.id is null
               )
            """)
    Page<ResortApartmentForRentDTO> findResort(@Param("locationName") String locationName, @Param("checkIn") Date checkIn, @Param("checkOut") Date checkOut, @Param("min") Long min, @Param("max") Long max, @Param("guest") int guest, @Param("numberBedsRoom") int numberBedsRoom, @Param("numberBathRoom") int numberBathRoom, @Param("listOfInRoomAmenity") Set<Long> listOfInRoomAmenity, @Param("listOfPropertyView") Set<Long> listOfPropertyView, @Param("listOfPropertyType") Set<Long> listOfPropertyType, @Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            select distinct r from Resort r
            inner join r.properties p
            where r.status = 'ACTIVE' and p.status = 'ACTIVE'
            and r.isDeleted = false and p.isDeleted = false
                """)
    List<Resort> getsListResortHaveProperty();
}
