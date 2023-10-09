package com.example.holidayswap.domain.mapper.property.timeFrame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TimeFrameMapper {
    TimeFrameMapper INSTANCE = Mappers.getMapper(TimeFrameMapper.class);

    TimeFrameResponse toDtoResponse(TimeFrame entity);

    TimeFrame toEntity(TimeFrameRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TimeFrameRequest dto, @MappingTarget TimeFrame entity);
}
