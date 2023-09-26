package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.vacation.PropertyViewRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyViewResponse;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyViewMapper;
import com.example.holidayswap.repository.property.PropertyViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.DUPLICATE_PROPERTY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyViewServiceImpl implements PropertyViewService {
    private final PropertyViewRepository propertyViewRepository;

    @Override
    public Page<PropertyViewResponse> gets(String name, Pageable pageable) {
        var dtoReponses = propertyViewRepository.findAllByPropertyViewNameContainingIgnoreCaseAndIsDeletedIsFalse(name, pageable);
        return dtoReponses.map(PropertyViewMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public PropertyViewResponse get(Long id) {
        var entity = propertyViewRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        return PropertyViewMapper.INSTANCE.toDtoResponse(entity);
    }

    @Override
    public PropertyViewResponse create(PropertyViewRequest dtoRequest) {
        if (propertyViewRepository.findByPropertyViewNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getPropertyViewName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);
        var entity = PropertyViewMapper.INSTANCE.toEntity(dtoRequest);
        return PropertyViewMapper.INSTANCE.toDtoResponse(propertyViewRepository.save(entity));
    }

    @Override
    public PropertyViewResponse update(Long id, PropertyViewRequest dtoRequest) {
        if (propertyViewRepository.findByPropertyViewNameEqualsIgnoreCaseAndIsDeletedIsFalse(dtoRequest.getPropertyViewName()).isPresent())
            throw new DuplicateRecordException(DUPLICATE_PROPERTY_TYPE);
        var entity = propertyViewRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        PropertyViewMapper.INSTANCE.updateEntityFromDTO(dtoRequest, entity);
        propertyViewRepository.save(entity);
        return PropertyViewMapper.INSTANCE.toDtoResponse(entity);
    }

    @Override
    public void delete(Long id) {
        var entity = propertyViewRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        entity.setDeleted(true);
        propertyViewRepository.save(entity);
    }
}
