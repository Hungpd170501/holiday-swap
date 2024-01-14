package com.example.holidayswap.repository.property.timeFrame;

import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
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

@Repository
public interface TimeFrameRepository extends JpaRepository<TimeFrame, Long> {
    @Query("""
            select v from TimeFrame v
            where v.coOwner.property.id = :propertyId
            and v.coOwner.user.userId = :userId
            and v.coOwner.roomId = :roomId
              """)
    Page<TimeFrame> findAllByPropertyIdAAndUserIdAndRoomId(
            @Param("propertyId") Long propertyId,
            @Param("userId") Long userId,
            @Param("roomId") String roomId,
            Pageable pageable);

    @Query("""
            select v from TimeFrame v
            where v.coOwner.property.id = :propertyId
            and v.coOwner.user.userId = :userId
            and v.coOwner.roomId = :roomId
            """)
    List<TimeFrame> findAllByPropertyIdAAndUserIdAndRoomId(
            @Param("propertyId") Long propertyId,
            @Param("userId") Long userId,
            @Param("roomId") String roomId
    );

//    @Query("select v from TimeFrame v where v.id = :id  ")
//    Optional<TimeFrame> findByIdAnAndStatusAndIsDeletedIsFalse(@Param("id") Long id, @Param("status") TimeFrameStatus timeFrameStatus);

    @Query("select v from TimeFrame v where v.id = :id     ")
    Optional<TimeFrame> findByIdAndIsDeletedIsFalse(@Param("id") Long id);

//    @Query(value = """
//            select tf from TimeFrame tf
//
//            where tf.propertyId = :propertyId
//            and tf.roomId =:roomId
//            and tf.weekNumber = :numberWeek
//            and tf.userId != :userId
//             and tf.isDeleted = false
//            """)
//    List<TimeFrame> findOverlapWith(
//            @Param("propertyId") Long propertyId,
//            @Param("roomId") String roomId,
//            @Param("numberWeek") int numberWeek,
//            @Param("userId") Long userId
//    );

//    @Query(value = """
//            select tf from TimeFrame tf
//            where tf.propertyId = :propertyId
//            and tf.userId=:userId
//            and tf.roomId =:roomId
//            and tf.weekNumber = :numberWeek
//            and tf.status != 'REJECTED'
//            and tf.isDeleted = false
//            """)
//    Optional<TimeFrame> findOverlapWithStatusIsNotReject(
//            @Param("propertyId") Long propertyId,
//            @Param("userId") Long userId,
//            @Param("roomId") String roomId,
//            @Param("numberWeek") int numberWeek
//    );

    @Query(value = """
            select tf.*
                                                                      from time_frame tf
                                                                      where tf.time_frame_id = :timeFrameId
                                                                        and ((:timeFrameStatus is null) or (tf.status = :timeFrameStatus))
                                                                        and extract(week from date(:startTime)) = tf.week_number
                                                                        and extract(week from date(:endTime)) = tf.week_number
                                                                         and tf.is_deleted = false
            """, nativeQuery = true)
    Optional<TimeFrame> isMatchingTimeFrames(
            @Param("timeFrameId") Long timeFrameId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("timeFrameStatus") String timeFrameStatus
    );

    @Query("select t from TimeFrame t where t.coOwner.id = ?1")
    Page<TimeFrame> findAllByCoOwnerId(Long coOwnerId, Pageable pageable);

    @Query(value = """
            select tf.*
            from time_frame tf
                     inner join co_owner co on
                co.co_owner_id = tf.co_owner_id
            where co.property_id = :propertyId
              and co.room_id = :roomId
              and tf.week_number = :weekNumber
              --and co.user_id != :userId
              and co.status = 'ACCEPTED'
              and co.is_deleted = false
              and extract(year
                          from
                          date (:startTime)) between extract(year from co.start_time) and extract(year from co.end_time)
            """, nativeQuery = true)
    List<TimeFrame> findByPropertyIdAndRoomIdAndWeekNumberDEEDEDDuplicate(@Param("propertyId") Long propertyId,
//                                                                          @Param("userId") Long userId,
                                                                          @Param("roomId") String roomId,
                                                                          @Param("startTime") LocalDate startTime,
                                                                          @Param("weekNumber") Integer weekNumber);

    @Query(value = """
            select tf.*
            from time_frame tf
                     inner join co_owner co on
                co.co_owner_id = tf.co_owner_id
            where co.property_id = :propertyId
              and co.room_id = :roomId
              and tf.week_number = :weekNumber
              --and co.user_id != :userId
              and co.type = 'DEEDED'
              and co.status = 'ACCEPTED'
              and co.is_deleted = false
            """, nativeQuery = true)
    List<TimeFrame> findByPropertyIdAndRoomIdAndWeekNumberDEEDEDDuplicate(@Param("propertyId") Long propertyId,
//                                                                          @Param("userId") Long userId,
                                                                          @Param("roomId") String roomId,
                                                                          @Param("weekNumber") Integer weekNumber);

    @Query(value = """
            SELECT tf.*
            FROM time_frame tf
                     INNER JOIN co_owner co ON
                co.co_owner_id = tf.co_owner_id
            WHERE co.property_id = :propertyId
              AND co.room_id = :roomId
              AND tf.week_number = :weekNumber
              --AND co.user_id != :userId
              --	and co.type = :type
              AND co.status = 'ACCEPTED'
              AND co.is_deleted = FALSE
              AND (EXTRACT(YEAR
                          FROM cast(:startTime as date)) BETWEEN EXTRACT(YEAR
                                                                         FROM co.start_time) AND EXTRACT(YEAR
                                                                                                         FROM co.end_time)
              OR (CAST(:endTime AS date) IS NULL
                OR
                   (EXTRACT(YEAR
                            FROM cast(:endTime as date)) BETWEEN EXTRACT(YEAR
                                                                         FROM co.start_time) AND EXTRACT(YEAR
                                                                                                         FROM co.end_time))))
            """, nativeQuery = true)
    List<TimeFrame> findByPropertyIdAndRoomIdAndWeekNumberRIGHTTOUSEDuplicate(@Param("propertyId") Long propertyId,
//                                                                              @Param("userId") Long userId,
                                                                              @Param("roomId") String roomId,
                                                                              @Param("startTime") LocalDate startTime,
                                                                              @Param("endTime") LocalDate endTime,
                                                                              @Param("weekNumber") Integer weekNumber);

    @Query(value = """
            SELECT TF.*
            FROM TIME_FRAME TF
                     INNER JOIN
                 CO_OWNER CO
                 ON CO.CO_OWNER_ID = TF.CO_OWNER_ID
            WHERE CO.PROPERTY_ID = :propertyId
              AND CO.ROOM_ID = :roomId
              AND CO.USER_ID = :userId
              AND CO.TYPE = :type
              AND TF.WEEK_NUMBER = :weekNumber
              AND CASE
                      WHEN :type = 'DEEDED'
                          THEN CO.TYPE = 'DEEDED' AND :startDate = CO.START_TIME
                      ELSE CO.TYPE = 'RIGHT_TO_USE' AND (DATE(:startDate) = DATE(CO.START_TIME) OR
                                                         DATE(:endDate) = DATE(CO.END_TIME))
                END
            """, nativeQuery = true)
    Optional<TimeFrame> findByPropertyIdAndUserIdAndRoomIdAndCoOwnerTypeAndWeekNumber(
            @Param("propertyId") Long propertyId,
            @Param("userId") Long userId,
            @Param("roomId") String roomId,
            @Param("type") String type,
            @Param("weekNumber") Integer weekNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
//    @Query(value = """
//            select tf from TimeFrame tf
//            where tf.roomId = :roomId
//            and tf.userId = :userId
//            and tf.propertyId = :propertyId
//            and tf.weekNumber = :weekNumber
//            and tf.isDeleted = false
//            """)
//    Optional<TimeFrame> findByCoOwnerId(
//            String roomId,
//            Long userId,
//            Long propertyId,
//            int weekNumber
//    );

}
