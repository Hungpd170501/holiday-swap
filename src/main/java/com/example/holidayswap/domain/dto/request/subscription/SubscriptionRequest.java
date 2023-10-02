package com.example.holidayswap.domain.dto.request.subscription;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private Long userId;
    private Long planId;
}
