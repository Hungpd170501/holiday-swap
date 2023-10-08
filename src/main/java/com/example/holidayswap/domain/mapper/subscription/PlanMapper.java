package com.example.holidayswap.domain.mapper.subscription;

import com.example.holidayswap.domain.dto.request.subscription.PlanRequest;
import com.example.holidayswap.domain.dto.response.subscription.PlanResponse;
import com.example.holidayswap.domain.entity.subscription.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);
    @Mapping(source = "image", target = "image", ignore = true)
    Plan toPlan(PlanRequest planRequest);

    PlanResponse toPlanResponse(Plan plan);
}
