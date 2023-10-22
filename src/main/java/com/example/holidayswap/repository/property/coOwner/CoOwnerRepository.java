package com.example.holidayswap.repository.property.coOwner;

import com.example.holidayswap.domain.dto.response.property.RoomDTO;
import com.example.holidayswap.domain.dto.response.resort.OwnerShipResponseDTO;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
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

public interface CoOwnerRepository extends JpaRepository<CoOwner, CoOwnerId> {
    @Query(value = """
            select co.property_id,
                   co.room_id,
                   co.user_id,
                   co.end_time,
                   co.is_deleted,
                   co.start_time,
                   co.status,
                   co.type
            from co_owner co
                     join property p on co.property_id = p.property_id
            where (:resortId is null or p.property_id = :resortId)
              and (:propertyId is null or co.property_id = :propertyId)
              and (:userId is null or co.user_id = :userId)
              and (:roomId is null or co.room_id = :roomId)
              and (:coOwnerStatus is null or co.status = :coOwnerStatus)
            """, nativeQuery = true)
    Page<CoOwner> findAllByResortIdPropertyIdAndUserIdAndRoomId(@Param("resortId") Long resortId, @Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId, @Param("coOwnerStatus") String coOwnerStatus, Pageable pageable);

    @Query("""
            select o from CoOwner o
            where o.id.propertyId = :propertyId
            and o.id.userId = :userId
            and o.id.roomId = :roomId
            and o.isDeleted = false""")
    Optional<CoOwner> findAllByPropertyIdAndUserIdAndRoomIdAndIsDeleteIsFalse(@Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId);

    @Query("select o from CoOwner o " + "where upper(o.id.roomId) = upper( :roomId)" + "and o.id.propertyId = :propertyId " + "and o.id.userId = :userId " + "and o.isDeleted = false ")
    Optional<CoOwner> findByPropertyIdAndUserIdAndIdRoomId(@Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId);

    @Query(value = """
                   SELECT PROPERTY_ID,
                   ROOM_ID,
                   USER_ID,
                   END_TIME,
                   IS_DELETED,
                   START_TIME,
                   STATUS,
                   TYPE
            FROM CO_OWNER O
            WHERE UPPER(O.ROOM_ID) = UPPER(:roomId)
              AND O.PROPERTY_ID = :propertyId
              AND O.USER_ID != :userId
              AND O.IS_DELETED = FALSE
              AND ((O.START_TIME BETWEEN :startTime AND :endTime)
                OR
                   (O.END_TIME BETWEEN :startTime AND :endTime)
                OR
                   (O.START_TIME < :startTime AND O.END_TIME > :endTime)
                OR
                   (O.END_TIME > :startTime AND O.END_TIME < :endTime)
                )
                        """, nativeQuery = true)
    List<CoOwner> checkOverlapsTimeOwnership(@Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "SELECT Distinct o.property_id, o.room_id from co_owner o", nativeQuery = true)
    List<OwnerShipResponseDTO> getAllDistinctOwnerShipWithoutUserId();

    @Query(value = """
            select property_id, room_id, user_id, end_time, is_deleted, start_time, status, type from co_owner co where co.property_id = :propertyId and co.user_id = :userId and co.room_id = :roomId and case when co.type = 'DEEDED' then(   ((:coOwnerStatus is null) or (co.status = :coOwnerStatus))) else ( ((:coOwnerStatus is null) or (co.status = :coOwnerStatus)) and extract(year from date(:startTime)) >= extract(year from date(co.start_time)) and extract(year from date(:endTime)) <= extract(year from date(co.end_time)) ) end
            """, nativeQuery = true)
    Optional<CoOwner> isMatchingCoOwner(@Param("propertyId") Long propertyId, @Param("userId") Long userId, @Param("roomId") String roomId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("coOwnerStatus") String coOwnerStatus);

    @Query(value = """
            select new com.example.holidayswap.domain.dto.response.property.RoomDTO (
            co,
            p
            )
            from CoOwner co
                 inner join co.property p on co.id.propertyId = p.id
                 inner join  co.timeFrames tf on tf.propertyId = co.id.propertyId and tf.userId= co.id.userId and tf.roomId = co.id.roomId
                 inner join tf.availableTimes at on at.timeFrameId = tf.id
            """)
    Page<RoomDTO> findHavingAvailableTime(@Param("checkIn") Date checkIn, @Param("checkOut") Date checkOut, @Param("min") double min, @Param("max") double max, Pageable pageable);
}
