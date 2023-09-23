package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.service.property.vacation.VacationService;
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

    //    private final PropertyContractService propertyContractService;
//    private final InRoomAmenityTypeService inRoomAmenityTypeService;
    private final VacationService vacationService;

    private final PropertyRepository propertyRepository;

    @Override
    public Page<PropertyResponse> gets(Pageable pageable) {
        Page<Property> propertyPage = propertyRepository.findAll(pageable);
        Page<PropertyResponse> propertyResponsePage = propertyPage.map(element -> {
            PropertyResponse toDtoResponse = PropertyMapper.INSTANCE.toDtoResponse(element);
//            toDtoResponse.setPropertyContracts(propertyContractService.gets(element.getId()));
            toDtoResponse.setPropertyImages(propertyImageService.gets(element.getId()));
//            toDtoResponse.setInRoomAmenityTypes(inRoomAmenityTypeService.gets(element.getId()));
            return toDtoResponse;
        });
        return propertyResponsePage;
    }

    @Override
    public PropertyResponse get(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var propertyResponse = PropertyMapper.INSTANCE.toDtoResponse(propertyFound);
//        propertyResponse.setPropertyContracts(propertyContractService.gets(propertyFound.getId()));
        propertyResponse.setPropertyImages(propertyImageService.gets(propertyFound.getId()));
//        propertyResponse.setInRoomAmenityTypes(inRoomAmenityTypeService.gets(propertyFound.getId()));
        return propertyResponse;
    }

    @Override
    public PropertyResponse create(Long userId,
                                   PropertyRegisterRequest propertyRegisterRequest,
                                   List<MultipartFile> propertyImages,
                                   List<MultipartFile> propertyContractImages) throws IOException {
        var property = PropertyMapper.INSTANCE.toEntity(propertyRegisterRequest);
//        property.setUserId(userId);
        property.setStatus(PropertyStatus.WAITING);
        var propertyCreated = propertyRepository.save(property);

        propertyImages.forEach(element -> {
            propertyImageService.create(propertyCreated.getId(), element);
        });

//        var contractCreated = propertyContractService.create(propertyCreated.getId(), propertyRegisterRequest.getPropertyContractRequest());
//        propertyContractImages.forEach(element -> {
//            contractImageService.create(contractCreated.getId(), element);
//        });

        propertyRegisterRequest.getVacation().forEach(element -> {
            vacationService.create(propertyCreated.getId(), element);
        });


        return PropertyMapper.INSTANCE.toDtoResponse(propertyCreated);
    }

    @Override
    public PropertyResponse update(Long id, PropertyUpdateRequest propertyUpdateRequest) {
        var property = propertyRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        PropertyMapper.INSTANCE.updateEntityFromDTO(propertyUpdateRequest, property);
        propertyRepository.save(property);
        return PropertyMapper.INSTANCE.toDtoResponse(property);
    }

    @Override
    public void delete(Long id) {
        var propertyFound = propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        propertyFound.setIsDeleted(true);
        propertyRepository.save(propertyFound);
    }
}
