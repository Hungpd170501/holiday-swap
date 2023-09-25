package com.example.holidayswap.domain.mapper.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortImageRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import com.example.holidayswap.domain.entity.resort.ResortImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResortImageMapper {
    ResortImageMapper INSTANCE = Mappers.getMapper(ResortImageMapper.class);

    ResortImage toEntity(ResortImageRequest dtoRequest);

    ResortImageResponse toDtoResponse(ResortImage entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ResortImageRequest dto, @MappingTarget ResortImage entity);
}
