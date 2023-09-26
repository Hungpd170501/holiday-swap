package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyTypeRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyTypeMapper;
import com.example.holidayswap.repository.property.PropertyTypeRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_PROPERTY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyTypeServiceImpl implements PropertyTypeService {
    private final PropertyTypeRespository propertyTypeRepository;

    @Override
    public Page<PropertyTypeResponse> gets(String name, Pageable pageable) {
        var propertyTypesPage = propertyTypeRepository.findAllByPropertyTypeNameContainingIgnoreCaseAndDeletedIsFalse(name, pageable);
        return propertyTypesPage.map(PropertyTypeMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public PropertyTypeResponse get(Long id) {
        var entity = propertyTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        return PropertyTypeMapper.INSTANCE.toDtoResponse(entity);
    }

    @Override
    public PropertyTypeResponse create(PropertyTypeRequest dtoRequest) {
        if (propertyTypeRepository.findByPropertyTypeNameEqualsIgnoreCaseAndDeletedIsFalse(dtoRequest.getPropertyTypeName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);
        var entity = PropertyTypeMapper.INSTANCE.toEntity(dtoRequest);
        return PropertyTypeMapper.INSTANCE.toDtoResponse(propertyTypeRepository.save(entity));
    }

    @Override
    public PropertyTypeResponse update(Long id, PropertyTypeRequest dtoRequest) {
        if (propertyTypeRepository.findByPropertyTypeNameEqualsIgnoreCaseAndDeletedIsFalse(dtoRequest.getPropertyTypeName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);
        var entity = propertyTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        PropertyTypeMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        propertyTypeRepository.save(entity);
        return PropertyTypeMapper.INSTANCE.toDtoResponse(entity);
    }

    @Override
    public void delete(Long id) {
        var entity = propertyTypeRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        entity.setDeleted(true);
        propertyTypeRepository.save(entity);
    }
}
