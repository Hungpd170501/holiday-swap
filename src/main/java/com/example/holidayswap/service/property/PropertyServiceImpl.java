package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.repository.property.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_NOT_FOUND;

@RequiredArgsConstructor
@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public Page<PropertyResponse> getListProperty(Long resortId, Pageable pageable) {
        Page<Property> propertyPage = propertyRepository.findAllByResort(resortId, pageable);
        Page<PropertyResponse> propertyResponses = propertyPage.map(propertyMapper::toPropertyResponse);
        return propertyResponses;
    }

    @Override
    public PropertyResponse getProperty(Long propertyId) {
        return propertyRepository.findById(propertyId).map(PropertyMapper.INSTANCE::toPropertyResponse)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));

    }

    @Override
    public void createProperty(PropertyRequest propertyRequest) {
        var property = PropertyMapper.INSTANCE.toProperty(propertyRequest);
        property.setStatus(PropertyStatus.WAITING);
        property.setIsDeleted(false);
        propertyRepository.save(property);
    }

    @Override
    public void updateProperty(Long propertyId, PropertyRequest propertyRequest) {
        var propertyUpdate = propertyMapper.INSTANCE.toProperty(propertyRequest);
        propertyUpdate.setId(propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND)).getId());
        propertyUpdate.setStatus(PropertyStatus.WAITING);
        propertyUpdate.setIsDeleted(false);
        propertyRepository.save(propertyUpdate);
    }

    @Override
    public void deleteProperty(Long propertyId) {
        var propertyFound = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        propertyFound.setIsDeleted(true);
        propertyRepository.save(propertyFound);
    }

    @Override
    public void acceptProperty(Long propertyId) {

    }

    @Override
    public void decline(Long propertyId) {

    }
}
