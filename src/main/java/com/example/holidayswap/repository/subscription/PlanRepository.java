package com.example.holidayswap.repository.subscription;

import com.example.holidayswap.domain.entity.subscription.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByPlanNameEquals(String planName);
}
