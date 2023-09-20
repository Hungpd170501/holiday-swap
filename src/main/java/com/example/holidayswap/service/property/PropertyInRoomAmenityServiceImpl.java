package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenity;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenityId;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.property.PropertyInRoomAmenityRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyInRoomAmenityServiceImpl implements PropertyInRoomAmenityService {
    private final PropertyInRoomAmenityRepository propertyInRoomAmenityRepository;

    private final PropertyRepository propertyRepository;
    private final InRoomAmenityRepository inRoomAmenityRepository;


    @Override
    public PropertyInRoomAmenity create(Long propertyId, Long inRoomAmenityId) {
        PropertyInRoomAmenity propertyInRoomAmenity = new PropertyInRoomAmenity(
                new PropertyInRoomAmenityId(propertyId, inRoomAmenityId),
                false,
                propertyRepository.findPropertyById(propertyId).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND)),
                inRoomAmenityRepository.findById(inRoomAmenityId).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND))
        );
        return propertyInRoomAmenityRepository.save(propertyInRoomAmenity);
    }

    @Override
    public PropertyInRoomAmenity update() {
        return null;
    }

    @Override
    public void delete(Long propertyId, Long inRoomAmenityId) {

    }
}
