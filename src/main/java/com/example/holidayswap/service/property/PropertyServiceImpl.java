package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenity;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.PropertyInRoomAmenityId;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.domain.mapper.property.inRoomAmenity.PropertyContractMapper;
import com.example.holidayswap.repository.property.PropertyContractRepository;
import com.example.holidayswap.repository.property.PropertyInRoomAmenityRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityRepository;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityTypeRepository;
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
    private final ContractImageService contractImageService;

    private final PropertyRepository propertyRepository;
    private final InRoomAmenityRepository inRoomAmenityRepository;
    private final InRoomAmenityTypeRepository inRoomAmenitiesTypeRepository;
    private final PropertyContractRepository propertyContractRepository;
    private final PropertyInRoomAmenityRepository propertyInRoomAmenityRepository;

//    private final PropertyMapper propertyMapper;

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
//        propertyResponse.setInRoomAmenityTypes(inRoomAmenitiesTypeRepository.findInRoomAmenityTypesByPropertyId(propertyFound.getId()));
        return propertyResponse;
    }

    @Override
    public PropertyResponse create(PropertyRegisterRequest propertyRegisterRequest,
                                   List<MultipartFile> propertyImages,
                                   List<MultipartFile> propertyContractImages) throws IOException {
        var property = PropertyMapper.INSTANCE.toEntity(propertyRegisterRequest);
        property.setStatus(PropertyStatus.WAITING);
        var propertyNew = propertyRepository.save(property);
        propertyImages.forEach(element -> {
            propertyImageService.create(propertyNew.getId(), element);
        });
        {
            var contract = PropertyContractMapper.INSTANCE.toEntity(propertyRegisterRequest.getPropertyContractRequest());
            contract.setPropertyId(propertyNew.getId());
            var contractNew = propertyContractRepository.save(contract);
            propertyContractImages.forEach(element -> {
                contractImageService.create(contractNew.getId(), element);
            });
        }
        {
            propertyRegisterRequest.getPropertyInRoomAmenityRequests().stream().toList().forEach(element -> {
                PropertyInRoomAmenity propertyInRoomAmenity = new PropertyInRoomAmenity(
                        new PropertyInRoomAmenityId(propertyNew.getId(), element.getInRoomAmenityId()),
                        false,
                        propertyNew,
                        inRoomAmenityRepository.findById(element.getInRoomAmenityId()).orElseThrow());
                propertyInRoomAmenityRepository.save(propertyInRoomAmenity);
            });
        }
        return PropertyMapper.INSTANCE.toDtoResponse(propertyNew);
    }

    @Override
    public PropertyResponse update(Long id, PropertyUpdateRequest propertyUpdateRequest) {
        var propertyFound = propertyRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        PropertyMapper.INSTANCE.updateEntityFromDTO(propertyUpdateRequest, propertyFound);
        propertyRepository.save(propertyFound);
        return PropertyMapper.INSTANCE.toDtoResponse(propertyFound);
    }

    @Override
    public void delete(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        propertyFound.setIsDeleted(true);
        propertyRepository.save(propertyFound);
    }
}
