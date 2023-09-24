package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyTypeRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.entity.property.PropertyType;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyTypeMapper;
import com.example.holidayswap.repository.property.PropertyTypeRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyTypeServiceImpl implements PropertyTypeService {
    private final PropertyTypeRespository propertyTypeRepository;

    @Override
    public Page<PropertyTypeResponse> gets(String name, Pageable pageable) {
        var propertyTypesPage = propertyTypeRepository.findPropertyTypesByPropertyTypeNameContainingIgnoreCaseAndDeletedIsFalse(name, pageable);
        var propertyTypesPageResponse = propertyTypesPage.map(PropertyTypeMapper.INSTANCE::toDtoResponse);
        return propertyTypesPageResponse;
    }

    @Override
    public PropertyTypeResponse get(Long id) {
        PropertyType propertyType = propertyTypeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        PropertyTypeResponse propertyImageResponse = PropertyTypeMapper.INSTANCE.toDtoResponse(propertyType);
        return propertyImageResponse;
    }

    @Override
    public PropertyTypeResponse create(PropertyTypeRequest propertyTypeRequest) {
        PropertyType propertyType = PropertyTypeMapper.INSTANCE.toEntity(propertyTypeRequest);
        var propertyTypeResponse = PropertyTypeMapper.INSTANCE.toDtoResponse(propertyTypeRepository.save(propertyType));
        return propertyTypeResponse;
    }

    @Override
    public PropertyTypeResponse update(Long id, PropertyTypeRequest propertyTypeRequest) {

        PropertyType propertyType = propertyTypeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        PropertyTypeMapper.INSTANCE.updateEntityFromDTO(propertyTypeRequest, propertyType);
        propertyTypeRepository.save(propertyType);
        return PropertyTypeMapper.INSTANCE.toDtoResponse(propertyType);

    }

    @Override
    public void delete(Long id) {
        PropertyType propertyType = propertyTypeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        propertyType.setDeleted(true);
        propertyTypeRepository.save(propertyType);
    }
}
