package com.example.holidayswap.domain.mapper.subscription;

import com.example.holidayswap.domain.dto.request.subscription.SubscriptionRequest;
import com.example.holidayswap.domain.dto.response.subscription.SubscriptionResponse;
import com.example.holidayswap.domain.entity.subscription.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);
    Subscription toSubscription(SubscriptionRequest subscriptionRequest);
    SubscriptionResponse toSubscriptionResponse(Subscription subscription);
}
