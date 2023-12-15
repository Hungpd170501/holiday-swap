package com.example.holidayswap.repository.property.timeFrame;

import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
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
public interface TimeFrameRepository extends JpaRepository<TimeFrame, Long> {
    @Query("""
            select v from TimeFrame v
            where v.coOwner.property.id = :propertyId
            and v.coOwner.user.userId = :userId
            and v.coOwner.id.roomId = :roomId
            and v.isDeleted = false """)
    Page<TimeFrame> findAllByPropertyIdAAndUserIdAndRoomId(
            @Param("propertyId") Long propertyId,
            @Param("userId") Long userId,
            @Param("roomId") String roomId,
            Pageable pageable);

    @Query("select v from TimeFrame v where v.id = :id and v.status = :status and  v.isDeleted = false")
    Optional<TimeFrame> findByIdAnAndStatusAndIsDeletedIsFalse(@Param("id") Long id, @Param("status") TimeFrameStatus timeFrameStatus);

    @Query("select v from TimeFrame v where v.id = :id and  v.isDeleted = false")
    Optional<TimeFrame> findByIdAndIsDeletedIsFalse(@Param("id") Long id);

    @Query(value = """
            select tf from TimeFrame tf
            where tf.propertyId = :propertyId
            and tf.roomId =:roomId
            and tf.weekNumber = :numberWeek
            and tf.userId != :userId
            """)
    List<TimeFrame> findOverlapWith(
            @Param("propertyId") Long propertyId,
            @Param("roomId") String roomId,
            @Param("numberWeek") int numberWeek,
            @Param("userId") Long userId
    );

    @Query(value = """
            select tf from TimeFrame tf
            where tf.propertyId = :propertyId
            and tf.userId=:userId
            and tf.roomId =:roomId
            and tf.weekNumber = :numberWeek
            and tf.status != 'REJECTED'
            """)
    Optional<TimeFrame> findOverlapWithStatusIsNotReject(
            @Param("propertyId") Long propertyId,
            @Param("userId") Long userId,
            @Param("roomId") String roomId,
            @Param("numberWeek") int numberWeek
    );

    @Query(value = """
            select time_frame_id, is_deleted, property_id, room_id, status, user_id, week_number
                                                                      from time_frame tf
                                                                      where tf.time_frame_id = :timeFrameId
                                                                        and ((:timeFrameStatus is null) or (tf.status = :timeFrameStatus))
                                                                        and extract(week from date(:startTime)) = tf.week_number
                                                                        and extract(week from date(:endTime)) = tf.week_number
            """, nativeQuery = true)
    Optional<TimeFrame> isMatchingTimeFrames(
            @Param("timeFrameId") Long timeFrameId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("timeFrameStatus") String timeFrameStatus
    );

    @Query(value = """
            select tf from TimeFrame tf
            where tf.roomId = :roomId
            and tf.userId = :userId
            and tf.propertyId = :propertyId
            and tf.weekNumber = :weekNumber
            """)
    Optional<TimeFrame> findByCoOwnerId(
            String roomId,
            Long userId,
            Long propertyId,
            int weekNumber
    );

}
