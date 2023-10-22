package com.example.holidayswap.repository.property.timeFrame;

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
}
