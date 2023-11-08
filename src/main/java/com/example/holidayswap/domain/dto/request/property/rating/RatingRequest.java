package com.example.holidayswap.domain.dto.request.property.rating;

import lombok.Data;

@Data
public class RatingRequest {
    private String comment;
    private double rating;
}
