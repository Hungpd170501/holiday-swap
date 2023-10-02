package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.amenity.InRoomAmenityRepository;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeService;
import com.example.holidayswap.service.property.ownership.ContractImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final InRoomAmenityRepository inRoomAmenityRepository;
    private final InRoomAmenityTypeService inRoomAmenityTypeService;
    private final PropertyImageService propertyImageService;
    private final ContractImageService contractImageService;
    private final PropertyMapper propertyMapper;

    @Override
    public Page<PropertyResponse> gets(Long resortId, Date timeCheckIn, Date timeCheckOut, Pageable pageable) {
        var entities = propertyRepository.
                findAllByResortIdAndIsDeleteIsFalseIncludeCheckInCheckOut(
                        resortId,
                        timeCheckIn,
                        timeCheckOut,
                        pageable);
        var dtoResponse = entities.
                map(propertyMapper::toDtoResponse);
        dtoResponse.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getId());
            var propertyImages = propertyImageService.gets(e.getId());
            e.setInRoomAmenityTypeResponses(inRoomAmenityTypeResponses);
            e.setPropertyImageResponses(propertyImages);
        });
        return dtoResponse;
    }

    @Override
    public PropertyResponse get(Long id) {
        var entity = propertyRepository.findPropertyById(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var dtoResponse = PropertyMapper.INSTANCE.toDtoResponse(entity);
        var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(entity.getId());
        var propertyImages = propertyImageService.gets(entity.getId());
        dtoResponse.setInRoomAmenityTypeResponses(inRoomAmenityTypeResponses);
        dtoResponse.setPropertyImageResponses(propertyImages);
        return dtoResponse;
    }

    @Override
    public List<PropertyResponse> getByResortId(Long resortId) {
        var entities = propertyRepository.
                findAllByResortIdAndIsDeleteIsFalse(resortId);

        var dtoResponse = entities.stream().map(propertyMapper::toDtoResponse).collect(Collectors.toList());

        dtoResponse.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getId());
            var propertyImages = propertyImageService.gets(e.getId());
            e.setInRoomAmenityTypeResponses(inRoomAmenityTypeResponses);
            e.setPropertyImageResponses(propertyImages);
        });
        return dtoResponse;
    }

    @Override
    public PropertyResponse create(Long userId,
                                   PropertyRegisterRequest dtoRequest,
                                   List<MultipartFile> propertyImages) {
        var entity = create(userId, dtoRequest);
        propertyImages.forEach(e -> {
            propertyImageService.create(entity.getId(), e);
        });
        propertyImages.forEach(e -> {
            ContractImageRequest contractImageRequest = new ContractImageRequest();
            contractImageRequest.setPropertyId(entity.getId());
            contractImageRequest.setUserId(userId);
            contractImageService.create(contractImageRequest, e);
        });
        return entity;
    }

    @Override
    public PropertyResponse create(Long userId, PropertyRegisterRequest dtoRequest) {
        var entity = PropertyMapper.INSTANCE.toEntity(dtoRequest);
        entity.setStatus(PropertyStatus.WAITING);
        List<InRoomAmenity> amenities = new ArrayList<>();
        dtoRequest.getInRoomAmenities().forEach(e -> {
            amenities.add(inRoomAmenityRepository.findByIdAndIsDeletedIsFalse(e).orElseThrow(
                    () -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND)));
        });
        entity.setInRoomAmenities(amenities);
        var created = propertyRepository.save(entity);
        return PropertyMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    public PropertyResponse update(Long id, PropertyUpdateRequest dtoRequest) {
        var property = propertyRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        PropertyMapper.INSTANCE.updateEntityFromDTO(dtoRequest, property);
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
