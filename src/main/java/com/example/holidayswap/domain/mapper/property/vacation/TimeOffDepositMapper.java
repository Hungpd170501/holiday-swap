package com.example.holidayswap.domain.mapper.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.TimeOffDepositRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.TimeOffDepositResponse;
import com.example.holidayswap.domain.entity.property.vacation.TimeOffDeposit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TimeOffDepositMapper {
    TimeOffDepositMapper INSTANCE = Mappers.getMapper(TimeOffDepositMapper.class);

    TimeOffDepositResponse toDtoResponse(TimeOffDeposit entity);

    TimeOffDeposit toEntity(TimeOffDepositRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TimeOffDepositRequest dto, @MappingTarget TimeOffDeposit entity);
}
