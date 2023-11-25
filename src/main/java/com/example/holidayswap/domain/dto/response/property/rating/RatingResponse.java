package com.example.holidayswap.domain.dto.response.property.rating;

import com.example.holidayswap.domain.entity.property.rating.RatingId;
import com.example.holidayswap.domain.entity.property.rating.RatingType;
import lombok.Data;

import java.util.Date;

@Data
public class RatingResponse {
    private RatingId id;
    private UserRatingResponse user;
    private String comment;
    private Double rating;
    private RatingType ratingType;
    private Date createDate;
    private Date updateDate;
}
