package com.example.holidayswap.domain.mapper.property.amenity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OwnershipMapper {
    OwnershipMapper INSTANCE = Mappers.getMapper(OwnershipMapper.class);

//    PropertyContractResponse toDtoResponse(Ownership propertyContract);
//
////    @Mapping(target = "property", ignore = true)
//    Ownership toEntity(PropertyContractRequest propertyContractRequest);
//
//    @Mapping(target = "id", ignore = true)
//    void updateEntityFromDTO(PropertyContractRequest dto, @MappingTarget Ownership entity);
}
