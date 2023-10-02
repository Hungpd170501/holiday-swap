package com.example.holidayswap.domain.dto.response.subscription;

import com.example.holidayswap.domain.entity.subscription.PlanPriceInterval;
import com.example.holidayswap.domain.entity.subscription.PriceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {
    private Long planId;
    private String planName;
    private String description;
    private String image;
    private double price;
    private PriceType priceType;
    private PlanPriceInterval planPriceInterval;
    private boolean isActive;
}
