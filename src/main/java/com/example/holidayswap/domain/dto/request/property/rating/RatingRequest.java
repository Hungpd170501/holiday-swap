package com.example.holidayswap.domain.dto.request.property.rating;

import com.example.holidayswap.domain.entity.property.rating.RatingType;
import lombok.Data;

@Data
public class RatingRequest {
    private String comment;
    private double rating;
    private RatingType ratingType;
    private Long bookingId;
}
