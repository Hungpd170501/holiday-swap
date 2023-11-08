package com.example.holidayswap.domain.mapper.property.rating;

import com.example.holidayswap.domain.dto.request.property.rating.RatingRequest;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
import com.example.holidayswap.domain.entity.property.rating.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingResponse toDtoResponse(Rating entity);

    Rating toEntity(RatingRequest dtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(RatingRequest dto, @MappingTarget Rating entity);
}
