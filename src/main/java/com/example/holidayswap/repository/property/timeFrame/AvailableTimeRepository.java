package com.example.holidayswap.repository.property.timeFrame;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
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

    @Query("""
            select t from AvailableTime t
            join t.timeFrame v
            join v.coOwner ow
            join ow.property p
            join  p.propertyType pt
            join pt.resorts r
            where r.id = :resortId and t.isDeleted = false""")
    Page<AvailableTime> findAllByResortIdAndDeletedFalse(@Param("resortId") Long resortId, Pageable pageable);

    @Query("""
            select t from AvailableTime t
            join t.timeFrame v
            join v.coOwner ow
            join ow.property p
            where p.id = :propertyId and t.isDeleted = false""")
    Page<AvailableTime> findAllByPropertyIdAndDeletedFalse(@Param("propertyId") Long propertyId, Pageable pageable);

    @Query("select t from AvailableTime t where t.id = ?1 and t.isDeleted = false")
    Optional<AvailableTime> findByIdAndDeletedFalse(Long id);

    @Query("""
            select tod from AvailableTime tod
            where tod.timeFrameId = ?1
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
            and tod.isDeleted = false 
            and (?4 is null or tod.status = ?4)""")
    Optional<AvailableTime> findOverlapsWhichAnyTimeDeposit(Long timeFrameId, Date startTime, Date endTime, AvailableTimeStatus timeOffDepositStatus);

    @Query(value = "select t.* from available_time t JOIN time_frame v on t.time_frame_id = v.time_frame_id where v.property_id = ?1 AND v.room_id = ?2 AND ((?3 BETWEEN t.start_time AND t.end_time) OR ( ?4 BETWEEN t.start_time AND t.end_time)) Order By t.start_time ASC", nativeQuery = true)
    List<AvailableTime> findAllByPropertyIdAndRoomIdBetweenDate(Long propertyId, String roomId, Date startTime, Date endTime);

    @Query(value = """
                    select av from AvailableTime av
                    inner join av.timeFrame af
                    inner join af.coOwner co
                    where co.id.roomId = :roomId
            """)
    List<AvailableTime> findAllByRoomId(@Param("roomId") String roomId);

    @Query(value = """
                    select at from AvailableTime at
                    inner join at.timeFrame tf
                    inner join tf.coOwner co
                    inner join co.property p
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
                 inner join at.timeFrame tf
                 inner join tf.coOwner co
                 inner join co.property p
                 inner join p.resort r
                 inner join co.user u
                 inner join p.inRoomAmenities ira
                 inner join p.propertyType pt
                 inner join p.propertyView pv
                 where
                 ((:resortId is null) or (r.id = :resortId))
                 and ((:min is null) or ( at.pricePerNight >= cast(:min as double))
                 and ((:max is null) or at.pricePerNight <= cast(:max as double)))
                 and (
                    ((cast(:checkIn as date ) is null) and (cast(:checkOut as date) is null))
                        or ((date(:checkOut) between date(at.startTime) and date(at.endTime))
                        and (date(:checkIn)) between date(at.startTime) and date(at.endTime))
                     )
                 and co.status = 'ACCEPTED'
                 and tf.status = 'ACCEPTED'
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
            """)
    Page<ApartmentForRentDTO> findApartmentForRent(@Param("locationName") String locationName, @Param("resortId") Long resortId, @Param("checkIn") Date checkIn, @Param("checkOut") Date checkOut, @Param("min") Long min, @Param("max") Long max, @Param("guest") int guest, @Param("numberBedsRoom") int numberBedsRoom, @Param("numberBathRoom") int numberBathRoom, @Param("listOfInRoomAmenity") Set<Long> listOfInRoomAmenity, @Param("listOfPropertyView") Set<Long> listOfPropertyView, @Param("listOfPropertyType") Set<Long> listOfPropertyType, Pageable pageable);

    @Query(value = """
            select distinct new com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO (
            co.id,
                    p, r, u, at
            )
                 from AvailableTime at
                 inner join at.timeFrame tf
                 inner join tf.coOwner co
                 inner join co.property p
                 inner join p.resort r
                 inner join co.user u
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
}
