package com.example.holidayswap.repository.property.timeFrame;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    @Query("select t from AvailableTime t where t.coOwnerId = ?1 and t.isDeleted = false")
    Page<AvailableTime> findAllByVacationUnitIdAndDeletedIsFalse(Long vacationId, Pageable pageable);

    @Query("select a from AvailableTime a where a.coOwnerId = ?1 and a.isDeleted = false")
    List<AvailableTime> findAllByTimeFrameIdAndIsDeletedIsFalse(Long timeFrameId);

    @Query("""
            select t from AvailableTime t
             
            join t.coOwner ow
            join ow.property p
            join  p.propertyType pt
            join pt.resorts r
            where r.id = :resortId and t.isDeleted = false""")
    Page<AvailableTime> findAllByResortIdAndDeletedFalse(@Param("resortId") Long resortId, Pageable pageable);


//    AvailableTime findByTimeFrameId(Long timeFrameId);

    @Query("""
            select t from AvailableTime t
                       
            join t.coOwner ow
            join ow.property p
            where p.id = :propertyId and t.isDeleted = false""")
    Page<AvailableTime> findAllByPropertyIdAndDeletedFalse(@Param("propertyId") Long propertyId, Pageable pageable);

    @Query("select t from AvailableTime t where t.id = ?1 and t.isDeleted = false")
    Optional<AvailableTime> findByIdAndDeletedFalse(Long id);

    @Query(value = "SELECT * FROM available_time t WHERE t.available_time_id = ?1 AND t.is_deleted = false AND ( date (?2) BETWEEN date (t.start_time) AND date (t.end_time)) AND ( date (?3) BETWEEN date( t.start_time) AND date ( t.end_time)) ", nativeQuery = true)
    Optional<AvailableTime> findAvailableTimeByIdAndStartTimeAndEndTime(Long timeFrameId, Date startTime, Date endTime);

    @Query(value = """
            select tod.available_time_id, tod.start_time, tod.end_time, tod.price_per_night, tod.is_deleted, status,
                                                      tod.time_frame_id from available_time  tod
                        where tod.time_frame_id = :time_frame_id
                        and (date(tod.start_time), date(tod.end_time)) overlaps (date(:start_time), date(:end_time))
                        and tod.is_deleted  = false
                        and (:status is null or tod.status = :status)""", nativeQuery = true)
    List<AvailableTime> findOverlapsWhichAnyTimeDeposit(
            @Param("time_frame_id") Long timeFrameId,
            @Param("start_time") Date startTime,
            @Param("end_time") Date endTime,
            @Param("status") String timeOffDepositStatus);

    @Query(value = "select t.* from available_time t JOIN time_frame v on t.time_frame_id = v.time_frame_id where v.property_id = ?1 AND v.room_id = ?2 AND ((?3 BETWEEN t.start_time AND t.end_time) OR ( ?4 BETWEEN t.start_time AND t.end_time)) Order By t.start_time ASC", nativeQuery = true)
    List<AvailableTime> findAllByPropertyIdAndRoomIdBetweenDate(Long propertyId, String roomId, Date startTime, Date endTime);

//    @Query(value = """
//                    select av from AvailableTime av
//                    left join av.coOwnerId af
//                    left join af.coOwner co
//                    where co.roomId = :roomId
//            """)
//    List<AvailableTime> findAllByRoomId(@Param("roomId") String roomId);

//    @Query(value = """
//                    select at from AvailableTime at
//                    left join at.timeFrame tf
//                    left join tf.coOwner co
//                    left join co.property p
//                    where co.propertyId = :propertyId
//                    and co.userId =  :userId
//                    and co.roomId = :roomId
//                    and co.status = 'ACCEPTED'
//                    and co.property.status = 'ACTIVE'
//                    and tf.status = 'ACCEPTED'
//            """)
//    List<AvailableTime> findAllByCoOwnerId(@Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
                            at
                         )
            from AvailableTime at
                     left join at.coOwner co
                     left join co.timeFrames tf
                     left join co.property p
                     left join p.resort r
                     left join co.user u
                     left join p.inRoomAmenities ira
                     left join p.propertyType pt
                     left join p.propertyView pv
                     left join at.bookings bk
                     left join r.resortMaintainces rM
            where
                ((:resortId is null) or (r.id = :resortId))
              and ((:min is null) or ( at.pricePerNight >= cast(:min as double))
                and ((:max is null) or at.pricePerNight <= cast(:max as double)))
              and (
                ((cast(:checkIn as date ) is null) and (cast(:checkOut as date) is null))
                    or ((date(at.startTime) between date(:checkIn) and date(:checkOut))
                    and (date(at.endTime)) between date(:checkIn) and date(:checkOut))
                    or ((date(:checkOut) between date(at.startTime) and date(at.endTime))
                    and (date(:checkIn)) between date(at.startTime) and date(at.endTime))
                )
              and co.status = 'ACCEPTED'
              and at.status = 'OPEN'
              and p.status = 'ACTIVE'
              and r.status = 'ACTIVE'
              and u.status = 'ACTIVE'
              and co.isDeleted = false
              and at.isDeleted = false
              and p.isDeleted = false
              and r.isDeleted = false
              and ((:#{#listOfInRoomAmenity == null} = true) or (ira.id in :listOfInRoomAmenity))
              and ((:#{#listOfPropertyView == null} = true) or (pv.id in :listOfPropertyView))
              and ((:#{#listOfPropertyType == null} = true) or (pt.id in :listOfPropertyType))
              and (p.numberKingBeds * 2
                + p.numberQueenBeds * 2
                + p.numberSingleBeds
                + p.numberDoubleBeds * 2
                + p.numberTwinBeds * 2
                + p.numberFullBeds * 2
                + p.numberMurphyBeds * 2
                + p.numberSofaBeds * 2) >= :guest
              and p.numberBedsRoom >= :numberBedsRoom
              and p.numberBathRoom >= :numberBathRoom
              AND (:locationName = '' OR unaccent(upper(r.locationFormattedName)) LIKE %:locationName%)
              and (:userId is null or co.user.userId  != :userId)
              and u.status = 'ACTIVE'
              and (
                case when bk.status = 5 and p.status = 'ACTIVE'
                    and r.status = 'ACTIVE' then (
                    (extract(day from cast(at.endTime as timestamp )) - extract(day from cast(at.startTime as timestamp )))
                        >
                    (select sum(extract(day from cast(bk.checkOutDate as timestamp )) - extract(day from cast(bk.checkInDate as timestamp ))) from Booking bk
                     where bk.availableTimeId = at.id))
                     else (
                         bk.id is null or bk.status != 5
                         )
                    end
                )
              and ((at.endTime) > current_date)
              and ((:#{#listPropertyCanNotUse == null} = true) or (co.propertyId not in :listPropertyCanNotUse))
              and ((:#{#listResortCanNotUse == null} = true) or (p.resortId not in :listResortCanNotUse))
              """)
    Page<ApartmentForRentDTO> findApartmentForRent(@Param("locationName") String locationName,
                                                   @Param("resortId") Long resortId, @Param("checkIn") Date checkIn,
                                                   @Param("checkOut") Date checkOut, @Param("min") Long min,
                                                   @Param("max") Long max, @Param("guest") int guest,
                                                   @Param("numberBedsRoom") int numberBedsRoom,
                                                   @Param("numberBathRoom") int numberBathRoom,
                                                   @Param("listOfInRoomAmenity") Set<Long> listOfInRoomAmenity,
                                                   @Param("listOfPropertyView") Set<Long> listOfPropertyView,
                                                   @Param("listOfPropertyType") Set<Long> listOfPropertyType,
                                                   @Param("listResortCanNotUse") Set<Long> listResortCanNotUse,
                                                   @Param("listPropertyCanNotUse") Set<Long> listPropertyCanNotUse,
                                                   @Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
                at 
            )
                 from AvailableTime at
                 left join at.coOwner co
                 left join co.timeFrames tf
                 left join co.property p
                 left join p.resort r
                 left join co.user u
                 where
                 at.id = :availableId
                 and co.status = 'ACCEPTED'
                
                 and at.status = 'OPEN'
                 and p.status = 'ACTIVE'
                 and r.status = 'ACTIVE'
                 and u.status = 'ACTIVE'
            """)
    Optional<ApartmentForRentDTO> findApartmentForRentByCoOwnerId(@Param("availableId") Long availableId);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
                at 
            )
            from AvailableTime at
                 left join at.coOwner co
                 left join co.timeFrames tf
                 left join co.property p
                 left join p.resort r
                 left join co.user u
                 left join p.inRoomAmenities ira
                 left join p.propertyType pt
                 left join p.propertyView pv
                 left join  at.bookings bk
                 where
               co.user.userId  = :userId
            """)
    Page<ApartmentForRentDTO> findApartmentForRentByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            select
                    	at2.*
                    from
                    	available_time at2
                    where
                    	at2.time_frame_id = :time_frame_id
                    	and extract (year
                    from
                    	at2.start_time) = :year
                    	and at2.status = 'OPEN'
                    	and is_deleted = false
                    """, nativeQuery = true)
    List<AvailableTime> findAllByTimeFrameIdAndYear(@Param("time_frame_id") Long timeFrameId, @Param("year") int year);

    @Query(value = """
            select at.*
            from available_time at
            where at.status = 'OPEN'
              and at.is_deleted = false
              and (at.start_time, at.end_time) overlaps (:startTime, :endTime)
              and at.co_owner_id = :coOwnerId
            """, nativeQuery = true)
    List<AvailableTime> isOverlaps(LocalDate startTime, LocalDate endTime, Long coOwnerId);

    @Query(value = "select a.* from available_time  a where a.co_owner_id  = :coOwnerId and EXTRACT (YEAR FROM A.start_time) = :year", nativeQuery = true)
    List<AvailableTime> findByCoOwnerIdAndYear(@Param("coOwnerId") Long coOwnerId, @Param("year") int year);

    Page<AvailableTime> findAllByCoOwnerIdAndIsDeletedIsFalse(Long coOwnerId, Pageable pageable);

    @Query(value = """      
            select a.*
            from available_time a
                     inner join public.co_owner co on a.co_owner_id = co.co_owner_id
            where co.co_owner_id = :co_owner_id
            and a.is_deleted = false
            """, nativeQuery = true)
    List<AvailableTime> findByCoOwnerId(@Param("co_owner_id") Long coOwnerId);
}
