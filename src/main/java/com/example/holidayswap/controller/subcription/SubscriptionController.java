package com.example.holidayswap.controller.subcription;

import com.example.holidayswap.domain.dto.request.subscription.SubscriptionRequest;
import com.example.holidayswap.domain.dto.response.subscription.SubscriptionResponse;
import com.example.holidayswap.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions() {
        var subscriptions = subscriptionService.getSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponse> createSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        var subscription = subscriptionService.createSubscription(subscriptionRequest);
        return ResponseEntity.ok(subscription);
    }
}
