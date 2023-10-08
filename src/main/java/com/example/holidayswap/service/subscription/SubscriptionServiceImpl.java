package com.example.holidayswap.service.subscription;

import com.example.holidayswap.domain.dto.request.subscription.SubscriptionRequest;
import com.example.holidayswap.domain.dto.response.subscription.SubscriptionResponse;
import com.example.holidayswap.domain.entity.subscription.*;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.subscription.SubscriptionMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.subscription.PlanRepository;
import com.example.holidayswap.repository.subscription.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Override
    public List<SubscriptionResponse> getSubscriptions() {
        return subscriptionRepository.findAll().stream().map(SubscriptionMapper.INSTANCE::toSubscriptionResponse).toList();
    }

    @Override
    @Transactional
    public SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest) {
        var subscription = SubscriptionMapper.INSTANCE.toSubscription(subscriptionRequest);
        var user = userRepository.findById(subscriptionRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        var plan = planRepository.findById(subscriptionRequest.getPlanId()).orElseThrow(() -> new EntityNotFoundException(PLAN_NOT_FOUND));
        subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        subscription.setUser(user);
        subscription.setPlan(plan);
        updateSubscriptionDetail(subscription, plan);
        subscription.setSubscriptionStatusEvents(
                List.of(SubscriptionStatusEvent.builder()
                        .subscriptionEventType(SubscriptionEventType.CREATED)
                        .subscription(subscription)
                        .build())
        );
        return SubscriptionMapper.INSTANCE.toSubscriptionResponse(subscriptionRepository.save(subscription));
    }

    private void updateSubscriptionDetail(Subscription subscription, Plan plan) {
        subscription.setCurrentPeriodStart(LocalDateTime.now());
        if (plan.getPlanPriceInterval().equals(PlanPriceInterval.MONTHLY)) {
            subscription.setCurrentPeriodEnd(LocalDateTime.now().plusMonths(1));
        } else if (plan.getPlanPriceInterval().equals(PlanPriceInterval.YEARLY)) {
            subscription.setCurrentPeriodEnd(LocalDateTime.now().plusYears(1));
        }
        subscription.setSubscriptionDetails(
                List.of(SubscriptionDetail.builder()
                        .planId(plan.getPlanId())
                        .price(plan.getPrice())
                        .periodStart(subscription.getCurrentPeriodStart())
                        .periodEnd(subscription.getCurrentPeriodEnd())
                        .subscription(subscription)
                        .build()));
    }

    @Override
    public SubscriptionResponse renewSubscriptions(Long subscriptionId) {
        var subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new EntityNotFoundException(SUBSCRIPTION_NOT_FOUND));
        var plan = subscription.getPlan();
        subscription.setSubscriptionStatusEvents(
                List.of(SubscriptionStatusEvent.builder()
                        .subscriptionEventType(SubscriptionEventType.RECURRING_PAYMENT)
                        .build())
        );
        updateSubscriptionDetail(subscription, plan);
        return SubscriptionMapper.INSTANCE.toSubscriptionResponse(subscriptionRepository.save(subscription));
    }
}
