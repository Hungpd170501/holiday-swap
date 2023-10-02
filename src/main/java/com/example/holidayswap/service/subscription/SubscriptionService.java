package com.example.holidayswap.service.subscription;

import com.example.holidayswap.domain.dto.request.subscription.SubscriptionRequest;
import com.example.holidayswap.domain.dto.response.subscription.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionResponse> getSubscriptions();

    SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest);

    SubscriptionResponse renewSubscriptions(Long subscriptionId);
}
