package com.example.holidayswap.repository.property.rate;

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
    @Query("select r from Rating r where r.property.id = :propertyId  ")
    Page<Rating> findAllByPropertyId(@Param("propertyId") Long propertyId, Pageable pageable);

    @Query("select avg(r.rating) from Rating r where r.property.id = :propertyId  ")
    Double calculateRating(@Param("propertyId") Long propertyId);

    @Query("select r from Rating r where r.property.id = :propertyId and r.user.userId = :userId ")
    Optional<Rating> findByPropertyIdAndUserUserIdAnd(@Param("propertyId") Long propertyId, @Param("userId") Long userId);
}
