package com.example.holidayswap.repository.subscription;

import com.example.holidayswap.domain.entity.subscription.SubscriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDetailRepository extends JpaRepository<SubscriptionDetail, Long> {
}
