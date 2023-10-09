package com.example.holidayswap.domain.mapper.property.timeFrame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AvailableTimeMapper {
    AvailableTimeMapper INSTANCE = Mappers.getMapper(AvailableTimeMapper.class);

    AvailableTimeResponse toDtoResponse(AvailableTime entity);

    AvailableTime toEntity(AvailableTimeRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AvailableTimeRequest dto, @MappingTarget AvailableTime entity);
}
