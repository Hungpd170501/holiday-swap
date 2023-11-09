package com.example.holidayswap.domain.dto.response.property.rating;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.property.rating.RatingId;
import lombok.Data;

@Data
public class RatingResponse {
    private RatingId id;
    private UserProfileResponse user;
    private String comment;
    private Double rating;
}
