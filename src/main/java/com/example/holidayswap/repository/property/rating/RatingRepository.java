package com.example.holidayswap.repository.property.rating;

import com.example.holidayswap.domain.entity.property.rating.Rating;
import com.example.holidayswap.domain.entity.property.rating.RatingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    @Query("""
            select r from Rating r inner join r.availableTime at inner join at.timeFrame tf inner join  tf.coOwner co
            where co.id.propertyId = :propertyId and co.id.roomId  = :roomId""")
    Page<Rating> findAllByPropertyIdAndRoomId(@Param("propertyId") Long propertyId, @Param("roomId") String roomId, Pageable pageable);

    @Query("""
            select avg(r.rating) from Rating r inner join r.availableTime at inner join at.timeFrame tf inner join  tf.coOwner co
            where co.id.propertyId = :propertyId and co.id.roomId  = :roomId
            """)
    Double calculateRating(@Param("propertyId") Long propertyId, @Param("roomId") String roomId);

    @Query("select r from Rating r where r.availableTime.id = :availableTimeId and r.user.userId = :userId ")
    Optional<Rating> findByAvailableTimeIdAndUserId(@Param("availableTimeId") Long availableTimeId, @Param("userId") Long userId);
}
