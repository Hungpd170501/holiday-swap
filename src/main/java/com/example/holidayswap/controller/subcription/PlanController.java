package com.example.holidayswap.controller.subcription;

import com.example.holidayswap.domain.dto.request.subscription.PlanRequest;
import com.example.holidayswap.domain.dto.response.subscription.PlanResponse;
import com.example.holidayswap.service.subscription.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/plan")
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getPlans() {
        var plans = planService.getPlans();
        return ResponseEntity.ok(plans);
    }


    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable("planId") Long planId) {
        var plan = planService.getPlanById(planId);
        return ResponseEntity.ok(plan);
    }

    @PostMapping(consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PlanResponse> createPlan(@ModelAttribute PlanRequest planRequest) throws IOException {
        var plan = planService.createPlan(planRequest);
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{planId}")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable("planId") Long planId, @RequestBody PlanRequest planRequest) {
        var plan = planService.updatePlan(planId, planRequest);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable("planId") Long planId) {
        planService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
