package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.inRoomAmenityType.InRoomAmenityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY_TYPE;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final InRoomAmenityTypeRepository inRoomAmenitiesTypeRepository;
    private final InRoomAmenityTypeRepository facilityTypeRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public Page<PropertyResponse> gets(Pageable pageable) {
        Page<Property> propertyPage = propertyRepository.findAll(pageable);
        Page<PropertyResponse> propertyResponsePage = propertyPage.map(propertyMapper::toPropertyResponse);
        return propertyResponsePage;
    }

    @Override
    public PropertyResponse get(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var propertyResponse = propertyMapper.toPropertyResponse(propertyFound);
        propertyResponse.setFacilityTypes(facilityTypeRepository.findFacilitiesTypesByPropertyId(propertyFound.getId()));
        return propertyResponse;
    }

    @Override
    public PropertyResponse create(PropertyRegisterRequest propertyRegisterRequest) {
        var property = propertyMapper.toProperty(propertyRegisterRequest);
        property.setStatus(PropertyStatus.WAITING);
        property.setIsDeleted(false);
        var propertyNew = propertyRepository.save(property);
        return propertyMapper.toPropertyResponse(propertyNew);
    }

    @Override
    public PropertyResponse update(Long id, PropertyUpdateRequest propertyUpdateRequest) {
        var propertyFound = propertyRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE));
        propertyMapper.updateEntityFromDTO(propertyUpdateRequest, propertyFound);
        propertyRepository.save(propertyFound);
        return propertyMapper.toPropertyResponse(propertyFound);
    }

    @Override
    public void delete(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        propertyFound.setIsDeleted(true);
        propertyRepository.save(propertyFound);
    }
}
