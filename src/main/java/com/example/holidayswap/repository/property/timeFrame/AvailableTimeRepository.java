package com.example.holidayswap.repository.property.timeFrame;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
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
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    @Query("select t from AvailableTime t where t.timeFrameId = ?1 and t.isDeleted = false")
    Page<AvailableTime> findAllByVacationUnitIdAndDeletedIsFalse(Long vacationId, Pageable pageable);

    @Query("select a from AvailableTime a where a.timeFrameId = ?1 and a.isDeleted = false")
    List<AvailableTime> findAllByTimeFrameIdAndIsDeletedIsFalse(Long timeFrameId);

    @Query("""
            select t from AvailableTime t
            join t.timeFrame v
            join v.coOwner ow
            join ow.property p
            join  p.propertyType pt
            join pt.resorts r
            where r.id = :resortId and t.isDeleted = false""")
    Page<AvailableTime> findAllByResortIdAndDeletedFalse(@Param("resortId") Long resortId, Pageable pageable);


    AvailableTime findByTimeFrameId(Long timeFrameId);

    @Query("""
            select t from AvailableTime t
            join t.timeFrame v
            join v.coOwner ow
            join ow.property p
            where p.id = :propertyId and t.isDeleted = false""")
    Page<AvailableTime> findAllByPropertyIdAndDeletedFalse(@Param("propertyId") Long propertyId, Pageable pageable);

    @Query("select t from AvailableTime t where t.id = ?1 and t.isDeleted = false")
    Optional<AvailableTime> findByIdAndDeletedFalse(Long id);

    @Query(value = "SELECT * FROM available_time t WHERE t.available_time_id = ?1 AND t.is_deleted = false AND (?2 BETWEEN t.start_time AND t.end_time) AND ( ?3 BETWEEN t.start_time AND t.end_time) ", nativeQuery = true)
    Optional<AvailableTime> findAvailableTimeByIdAndStartTimeAndEndTime(Long timeFrameId,Date startTime, Date endTime);

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

    @Query(value = """
                    select av from AvailableTime av
                    left join av.timeFrame af
                    left join af.coOwner co
                    where co.id.roomId = :roomId
            """)
    List<AvailableTime> findAllByRoomId(@Param("roomId") String roomId);

    @Query(value = """
                    select at from AvailableTime at
                    left join at.timeFrame tf
                    left join tf.coOwner co
                    left join co.property p
                    where co.id.propertyId = :propertyId
                    and co.id.userId =  :userId
                    and co.id.roomId = :roomId
                    and co.status = 'ACCEPTED'
                    and co.property.status = 'ACTIVE'
                    and tf.status = 'ACCEPTED'
            """)
    List<AvailableTime> findAllByCoOwnerId(@Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
            co.id,
            p, r, u, at
            )
            from AvailableTime at
                 left join at.timeFrame tf
                 left join tf.coOwner co
                 left join co.property p
                 left join p.resort r
                 left join co.user u
                 left join p.inRoomAmenities ira
                 left join p.propertyType pt
                 left join p.propertyView pv
                 left join  at.bookings bk
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
                 and tf.status = 'ACCEPTED'
                 and at.status = 'OPEN'
                 and p.status = 'ACTIVE'
                 and r.status = 'ACTIVE'
                 and u.status = 'ACTIVE'
                 and co.isDeleted = false
                 and tf.isDeleted = false
                 and at.isDeleted = false
                 and p.isDeleted = false
                 and r.isDeleted = false
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
               and (:userId is null or co.id.userId  != :userId)
               and u.status = 'ACTIVE'
               and (
                    case when bk.status = 5 then (
                           (extract(day from cast(at.endTime as timestamp )) - extract(day from cast(at.startTime as timestamp )))
                           >
                           (select sum(extract(day from cast(bk.checkOutDate as timestamp )) - extract(day from cast(bk.checkInDate as timestamp ))) from Booking bk
                           where bk.availableTimeId = at.id))
                       else (
                            bk.id is null or bk.status != 5
                       ) 
                       end 
                    )
               and (at.startTime > current_date and (at.endTime) > current_date)
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
                                                   @Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
            co.id,
                    p, r, u, at
            )
                 from AvailableTime at
                 left join at.timeFrame tf
                 left join tf.coOwner co
                 left join co.property p
                 left join p.resort r
                 left join co.user u
                 where
                 at.id = :availableId
                 and co.status = 'ACCEPTED'
                 and tf.status = 'ACCEPTED'
                 and at.status = 'OPEN'
                 and p.status = 'ACTIVE'
                 and r.status = 'ACTIVE'
                 and u.status = 'ACTIVE'
            """)
    Optional<ApartmentForRentDTO> findApartmentForRentByCoOwnerId(@Param("availableId") Long availableId);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
            co.id,
            p, r, u, at
            )
            from AvailableTime at
                 left join at.timeFrame tf
                 left join tf.coOwner co
                 left join co.property p
                 left join p.resort r
                 left join co.user u
                 left join p.inRoomAmenities ira
                 left join p.propertyType pt
                 left join p.propertyView pv
                 left join  at.bookings bk
                 where
               co.id.userId  = :userId
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
}
