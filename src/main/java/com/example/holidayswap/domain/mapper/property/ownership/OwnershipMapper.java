package com.example.holidayswap.domain.mapper.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.OwnershipResponse;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OwnershipMapper {
    OwnershipMapper INSTANCE = Mappers.getMapper(OwnershipMapper.class);

    OwnershipResponse toDtoResponse(Ownership entity);

    @Mapping(target = "id", ignore = true)
    Ownership toEntity(OwnershipRequest propertyContractRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(OwnershipRequest dto, @MappingTarget Ownership entity);
}
