package com.example.holidayswap.service.property.rate;

import com.example.holidayswap.domain.dto.request.property.rating.RatingRequest;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    Page<RatingResponse> getListRatingByPropertyId(Long propertyId, Pageable pageable);

    Double getRatingOfProperty(Long propertyId);

    RatingResponse getRatingByPropertyIdAndUserId(Long propertyId, Long userId);

    void create(Long propertyId, Long userId, RatingRequest ratingRequest);

    void update(Long propertyId, Long userId, RatingRequest ratingRequest);

    void deleteRatingById(Long propertyId, Long userId);
}
