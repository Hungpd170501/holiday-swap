package com.example.holidayswap.domain.mapper.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoOwnerMapper {
    CoOwnerMapper INSTANCE = Mappers.getMapper(CoOwnerMapper.class);

    CoOwnerResponse toDtoResponse(CoOwner entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeFrames", ignore = true)
    CoOwner toEntity(CoOwnerRequest propertyContractRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeFrames", ignore = true)
    void updateEntityFromDTO(CoOwnerRequest dto, @MappingTarget CoOwner entity);
}
