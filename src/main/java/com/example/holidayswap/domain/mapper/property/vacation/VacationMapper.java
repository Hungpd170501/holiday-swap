package com.example.holidayswap.domain.mapper.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationResponse;
import com.example.holidayswap.domain.entity.property.vacation.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VacationMapper {
    VacationMapper INSTANCE = Mappers.getMapper(VacationMapper.class);

    VacationResponse toDtoResponse(Vacation contractImage);

    Vacation toEntity(VacationRequest contractImageRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(VacationRequest dto, @MappingTarget Vacation entity);
}
