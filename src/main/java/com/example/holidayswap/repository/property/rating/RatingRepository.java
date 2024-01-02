package com.example.holidayswap.repository.property.rating;

import com.example.holidayswap.domain.entity.property.rating.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = """
            SELECT r.*
            FROM Rating r
                     INNER JOIN booking b ON
                b.book_id = r.rating_id
                     INNER JOIN available_time a ON
                b.available_time_id = a.available_time_id
                     INNER JOIN co_owner co ON
                co.co_owner_id = a.co_owner_id
            WHERE co.property_id = :propertyId
              AND co.room_id = :roomId""", nativeQuery = true)
    Page<Rating> findAllByPropertyIdAndRoomId(@Param("propertyId") Long propertyId, @Param("roomId") String roomId, Pageable pageable);

    @Query(value = """
            SELECT avg(r.rating)
            FROM rating r
            INNER JOIN booking b ON
            b.book_id = r.rating_id
            INNER JOIN available_time a ON
            b.available_time_id = a.available_time_id
            INNER JOIN co_owner co ON
            co.co_owner_id = a.co_owner_id
            WHERE co.property_id = :propertyId
            AND co.room_id = :roomId
            """, nativeQuery = true)
    Double calculateRating(@Param("propertyId") Long propertyId, @Param("roomId") String roomId);

    @Query("select r from Rating r where r.booking.id = :bookingId")
    Optional<Rating> findByBookingId(@Param("bookingId") Long bookingId);
}
