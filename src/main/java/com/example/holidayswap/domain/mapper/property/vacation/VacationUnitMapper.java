package com.example.holidayswap.domain.mapper.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationUnitRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationUnitResponse;
import com.example.holidayswap.domain.entity.property.vacation.VacationUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VacationUnitMapper {
    VacationUnitMapper INSTANCE = Mappers.getMapper(VacationUnitMapper.class);

    VacationUnitResponse toDtoResponse(VacationUnit entity);

    VacationUnit toEntity(VacationUnitRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(VacationUnitRequest dto, @MappingTarget VacationUnit entity);
}
