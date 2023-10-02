package com.example.holidayswap.service.subscription;

import com.example.holidayswap.domain.dto.request.subscription.PlanRequest;
import com.example.holidayswap.domain.dto.response.subscription.PlanResponse;
import com.example.holidayswap.domain.entity.subscription.Plan;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.subscription.PlanMapper;
import com.example.holidayswap.repository.subscription.PlanRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final FileService fileService;

    @Override
    public List<PlanResponse> getPlans() {
        return planRepository.findAll().stream().filter(Plan::isActive).map(PlanMapper.INSTANCE::toPlanResponse).toList();
    }

    @Override
    public PlanResponse getPlanById(Long planId) {
        return planRepository.findById(planId).map(PlanMapper.INSTANCE::toPlanResponse).orElseThrow(() -> new EntityNotFoundException(PLAN_NOT_FOUND));
    }

    @Override
    public PlanResponse updatePlan(Long planId, PlanRequest planRequest) {
        return planRepository.findById(planId).map(plan -> {
            plan.setPlanName(planRequest.getPlanName());
            planRepository.findByPlanNameEquals(planRequest.getPlanName()).ifPresent(r -> {
                throw new DataIntegrityViolationException(ROLE_ALREADY_EXISTS);
            });
            return PlanMapper.INSTANCE.toPlanResponse(planRepository.save(plan));
        }).orElseThrow(() -> new EntityNotFoundException(PLAN_NOT_FOUND));
    }

    @Override
    public void deletePlan(Long planId) {
        planRepository.findById(planId)
                .ifPresentOrElse(
                        planRepository::delete,
                        () -> {
                            throw new EntityNotFoundException(PLAN_NOT_FOUND);
                        });
    }

    @Override
    public PlanResponse createPlan(PlanRequest planRequest) throws IOException {
        planRepository.findByPlanNameEquals(planRequest.getPlanName()).ifPresent(role -> {
            throw new DataIntegrityViolationException(PLAN_ALREADY_EXISTS);
        });
        Plan plan = PlanMapper.INSTANCE.toPlan(planRequest);
        if (planRequest.getImage() != null) {
            plan.setImage(fileService.uploadFile(planRequest.getImage()));
        }
        plan.setActive(true);
        return PlanMapper.INSTANCE.toPlanResponse(planRepository.save(plan));
    }
}
