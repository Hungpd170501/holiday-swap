package com.example.holidayswap.repository.property.ownership;

import com.example.holidayswap.domain.dto.response.resort.OwnerShipResponseDTO;
import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.ContractType;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface OwnershipRepository extends JpaRepository<Ownership, OwnershipId> {
    //    @Query("select o from Ownership o where o.property.id = ?1 and o.user.userId = ?2 and o.isDeleted = false ")
    @Query("select o from Ownership o where o.property.id = ?1 and o.isDeleted = false")
    List<Ownership> findAllByPropertyIdAndIsDeletedIsFalse(Long propertyId);

    @Query("select o from Ownership o where o.user.userId = ?1 and o.isDeleted = false")
    List<Ownership> findAllByUserIdAndIsDeletedIsFalse(Long userId);

    @Query("select o from Ownership o where o.property.id = ?1 and o.user.userId = ?2 and o.isDeleted = false ")
    Optional<Ownership> findAllByPropertyIdAndUserIdAndIsDeleteIsFalse(Long propertyId, Long userId);

    @Query("select o from Ownership o " +
           "where upper(o.id.roomId) = upper( :roomId)" +
           "and o.id.propertyId = :propertyId " +
           "and o.id.userId = :userId " +
           "and o.isDeleted = false ")
    Optional<Ownership> findByPropertyIdAndUserUserIdAndIdRoomId(@Param("propertyId") Long propertyId,
                                                                 @Param("userId") Long userId,
                                                                 @Param("roomId") String roomId);

    @Query("select o from Ownership o " +
           "where upper(o.id.roomId) = upper( :roomId)" +
           "and o.id.propertyId = :propertyId " +
           "and o.isDeleted = false ")
    List<Ownership> findByPropertyIdAndIdRoomId(@Param("propertyId") Long propertyId, @Param("roomId") String roomId);

    @Query(value = """
            SELECT PROPERTY_ID,
                                 ROOM_ID,
                                 USER_ID,
                                 END_TIME,
                                 IS_DELETED,
                                 START_TIME,
                                 STATUS,
                                 TYPE
                          FROM OWNERSHIP O
                          WHERE UPPER(O.ROOM_ID) = UPPER(:roomId)
                            AND O.PROPERTY_ID = :propertyId
                            AND O.USER_ID != :userId
                            AND O.IS_DELETED = FALSE
                            AND CASE
                                    WHEN O.TYPE = 'RIGHT_TO_USE'
                                        then ((O.START_TIME BETWEEN :startTime AND :endTime)
                                            OR
                                             (O.END_TIME BETWEEN :startTime AND :endTime)
                                            OR
                                             (O.START_TIME < :startTime AND O.END_TIME > :endTime)
                                            OR
                                             (O.END_TIME > :startTime AND O.END_TIME < :endTime)
                                        )
                                    ELSE O.IS_DELETED = FALSE and O.TYPE = 'DEEDED'
                              END
            """, nativeQuery = true)
    Optional<Ownership> checkOverlapsTimeOwnership(@Param("propertyId") Long propertyId,
                                                   @Param("userId") Long userId,
                                                   @Param("roomId") String roomId,
                                                   @Param("startTime") Date startTime,
                                                   @Param("endTime") Date endTime
    );

    @Query("""
            select o from Ownership o
            where o.id.propertyId = ?1
            and o.id.userId = ?2
            and o.id.roomId = ?3
            and o.startTime >= ?4
            and o.endTime <= ?5
            and o.type = ?6
            and o.status = ?7
            and o.isDeleted = false""")
    Optional<Ownership> findByTypeIsRightToUse(
            Long propertyId,
            Long userId,
            String roomId,
            Date startTime,
            Date endTime,
            ContractType type,
            ContractStatus status);

    @Query("""
            select o from Ownership o
            where o.id.propertyId = ?1
            and o.id.userId = ?2
            and o.id.roomId = ?3
            and o.type = ?4
            and o.status = ?5
            and o.isDeleted = false""")
    Optional<Ownership> findByTypeIsDeeded(
            Long propertyId,
            Long userId,
            String roomId,
            ContractType type,
            ContractStatus status);

    @Query(value = "SELECT Distinct o.property_id, o.room_id from ownership o",nativeQuery = true)
    List<OwnerShipResponseDTO> getAllDistinctOwnerShipWithoutUserId();
}
