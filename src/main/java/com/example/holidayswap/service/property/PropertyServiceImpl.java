package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.repository.property.PropertyRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyImageService propertyImageService;

    private final PropertyContractService propertyContractService;
    private final ContractImageService contractImageService;
    private final PropertyInRoomAmenityService propertyInRoomAmenityService;

    private final PropertyRepository propertyRepository;

    @Override
    public Page<PropertyResponse> gets(Pageable pageable) {
        Page<Property> propertyPage = propertyRepository.findAll(pageable);
        Page<PropertyResponse> propertyResponsePage = propertyPage.map(PropertyMapper.INSTANCE::toDtoResponse);
        return propertyResponsePage;
    }

    @Override
    public PropertyResponse get(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var propertyResponse = PropertyMapper.INSTANCE.toDtoResponse(propertyFound);
        return propertyResponse;
    }

    @Override
    public Property create(Long userId,
                           PropertyRegisterRequest propertyRegisterRequest,
                           List<MultipartFile> propertyImages,
                           List<MultipartFile> propertyContractImages) throws IOException {
        var property = PropertyMapper.INSTANCE.toEntity(propertyRegisterRequest);
        property.setUserId(userId);
        property.setStatus(PropertyStatus.WAITING);
        var propertyCreated = propertyRepository.save(property);
        propertyImages.forEach(element -> {
            propertyImageService.create(propertyCreated.getId(), element);
        });
        {
            var contractCreated = propertyContractService.create(propertyCreated.getId(), propertyRegisterRequest.getPropertyContractRequest());
            propertyContractImages.forEach(element -> {
                contractImageService.create(contractCreated.getId(), element);
            });
        }
        {
            propertyRegisterRequest.getPropertyInRoomAmenityRequests().stream().toList().forEach(element -> {
                propertyInRoomAmenityService.create(propertyCreated.getId(), element.getInRoomAmenityId());
            });
        }
        return propertyCreated;
    }

    @Override
    public Property update(Long id, PropertyUpdateRequest propertyUpdateRequest) {
        var property = propertyRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        PropertyMapper.INSTANCE.updateEntityFromDTO(propertyUpdateRequest, property);
        propertyRepository.save(property);
        return property;
    }

    @Override
    public void delete(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        propertyFound.setIsDeleted(true);
        propertyRepository.save(propertyFound);
    }
}
