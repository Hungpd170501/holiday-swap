package com.example.holidayswap.domain.dto.response.subscription;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.entity.subscription.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private Long subscriptionId;
    private LocalDateTime createdOn;
    private LocalDateTime lastModifiedOn;
    private LocalDateTime currentPeriodStart;
    private LocalDateTime currentPeriodEnd;
    private SubscriptionStatus subscriptionStatus;
    private UserProfileResponse user;
    private PlanResponse plan;
}
