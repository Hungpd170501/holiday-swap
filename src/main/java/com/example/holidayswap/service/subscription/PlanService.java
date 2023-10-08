package com.example.holidayswap.service.subscription;

import com.example.holidayswap.domain.dto.request.subscription.PlanRequest;
import com.example.holidayswap.domain.dto.response.subscription.PlanResponse;

import java.io.IOException;
import java.util.List;

public interface PlanService {
    List<PlanResponse> getPlans();

    PlanResponse getPlanById(Long planId);

    PlanResponse updatePlan(Long planId, PlanRequest planRequest);

    void deletePlan(Long planId);

    PlanResponse createPlan(PlanRequest planRequest) throws IOException;
}
