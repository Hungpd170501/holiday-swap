package com.example.holidayswap.repository.subscription;

import com.example.holidayswap.domain.entity.subscription.SubscriptionStatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionStatusEventRepository extends JpaRepository<SubscriptionStatusEvent, Long> {
}
