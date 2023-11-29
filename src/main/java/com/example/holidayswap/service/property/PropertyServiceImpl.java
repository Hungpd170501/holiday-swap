package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.amenity.InRoomAmenity;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyMapper;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.PropertyTypeRespository;
import com.example.holidayswap.repository.property.PropertyViewRepository;
import com.example.holidayswap.repository.property.amenity.InRoomAmenityRepository;
import com.example.holidayswap.repository.property.rating.RatingRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final InRoomAmenityRepository inRoomAmenityRepository;
    private final InRoomAmenityTypeService inRoomAmenityTypeService;
    private final PropertyImageService propertyImageService;
    private final PropertyMapper propertyMapper;
    private final PropertyViewRepository propertyViewRepository;
    private final PropertyTypeRespository propertyTypeRespository;
    private final ResortRepository resortRepository;
    private final UserService userService;
    private final RatingRepository ratingRepository;

    @Override
    public Page<PropertyResponse> gets(Long[] resortId, String propertyName, PropertyStatus[] propertyStatus,
                                       Pageable pageable) {
        Page<Property> entities = null;
        entities = propertyRepository.findAllByFilter(resortId, propertyName, propertyStatus, pageable);
        var dtoResponse = entities.map(propertyMapper::toDtoResponse);
        dtoResponse.forEach(e -> {
            var propertyImages = propertyImageService.gets(e.getId());
            e.setPropertyImage(propertyImages);
//            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getId());
//            e.setInRoomAmenityType(inRoomAmenityTypeResponses);
//            e.setRating(ratingRepository.calculateRating(e.getId()));
        });
        return dtoResponse;
    }

    @Override
    public PropertyResponse get(Long id) {
        var entity = propertyRepository.findPropertyByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var dtoResponse = PropertyMapper.INSTANCE.toDtoResponse(entity);
        var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(entity.getId());
        var propertyImages = propertyImageService.gets(entity.getId());
        dtoResponse.setInRoomAmenityType(inRoomAmenityTypeResponses);
        dtoResponse.setPropertyImage(propertyImages);
        return dtoResponse;
    }

    @Override
    public List<PropertyResponse> getByResortId(Long resortId) {
        var entities = propertyRepository.findAllByResortIdAndIsDeleteIsFalse(resortId);

        var dtoResponse = entities.stream().map(propertyMapper::toDtoResponse).collect(Collectors.toList());

        dtoResponse.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getId());
            var propertyImages = propertyImageService.gets(e.getId());
            e.setInRoomAmenityType(inRoomAmenityTypeResponses);
            e.setPropertyImage(propertyImages);
        });
        return dtoResponse;
    }

    @Override
    @Transactional
    public PropertyResponse create(PropertyRegisterRequest dtoRequest, List<MultipartFile> propertyImages) {
        var entity = create(dtoRequest);
        propertyImages.forEach(e -> {
            propertyImageService.create(entity.getId(), e);
        });
        return entity;
    }

    @Override
    @Transactional
    public PropertyResponse create(PropertyRegisterRequest dtoRequest) {
        if (dtoRequest.getNumberKingBeds() == 0 && dtoRequest.getNumberQueenBeds() == 0 && dtoRequest.getNumberSingleBeds() == 0 && dtoRequest.getNumberDoubleBeds() == 0 && dtoRequest.getNumberTwinBeds() == 0 && dtoRequest.getNumberFullBeds() == 0 && dtoRequest.getNumberSofaBeds() == 0 && dtoRequest.getNumberMurphyBeds() == 0)
            throw new DataIntegrityViolationException("Property must have 1 number bed.");
        var entity = PropertyMapper.INSTANCE.toEntity(dtoRequest);
        entity.setStatus(PropertyStatus.ACTIVE);
        var resort = resortRepository.findById(dtoRequest.getPropertyViewId()).orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        if (resort.isDeleted()) throw new DataIntegrityViolationException("Resort has been deleted!.");
        if (resort.getStatus() == ResortStatus.ACTIVE) throw new DataIntegrityViolationException("Resort not ACTIVE!.");
        propertyViewRepository.findByIdAndIsDeletedIsFalse(dtoRequest.getPropertyViewId()).orElseThrow(() -> new EntityNotFoundException(PROPERTY_VIEW_NOT_FOUND));
        propertyTypeRespository.findPropertyTypeIsInResort(dtoRequest.getPropertyTypeId(), resort.getId()).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        List<InRoomAmenity> amenities = new ArrayList<>();
        dtoRequest.getInRoomAmenities().forEach(e -> {
            amenities.add(inRoomAmenityRepository.findByIdAndIsDeletedIsFalse(e).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND)));
        });
        entity.setInRoomAmenities(amenities);
        var created = propertyRepository.save(entity);
        return PropertyMapper.INSTANCE.toDtoResponse(created);
    }

    /*update only field name, description
    update
     */
    @Override
    public void update(Long id, PropertyUpdateRequest dtoRequest, List<MultipartFile> propertyImages) {
        var property = propertyRepository.findPropertyByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        if (dtoRequest.getNumberKingBeds() == 0 && dtoRequest.getNumberQueenBeds() == 0 && dtoRequest.getNumberSingleBeds() == 0 && dtoRequest.getNumberDoubleBeds() == 0 && dtoRequest.getNumberTwinBeds() == 0 && dtoRequest.getNumberFullBeds() == 0 && dtoRequest.getNumberSofaBeds() == 0 && dtoRequest.getNumberMurphyBeds() == 0)
            throw new DataIntegrityViolationException("Property must have 1 number bed.");
        PropertyMapper.INSTANCE.updateEntityFromDTO(dtoRequest, property);
        resortRepository.findByIdAndDeletedFalseAndResortStatus(dtoRequest.getPropertyViewId(), ResortStatus.ACTIVE).orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        propertyViewRepository.findByIdAndIsDeletedIsFalse(dtoRequest.getPropertyViewId()).orElseThrow(() -> new EntityNotFoundException(PROPERTY_VIEW_NOT_FOUND));
        propertyTypeRespository.findByIdAndIsDeletedFalse(dtoRequest.getPropertyTypeId()).orElseThrow(() -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND));
        List<InRoomAmenity> amenities = new ArrayList<>();
        dtoRequest.getInRoomAmenities().forEach(e -> {
            amenities.add(inRoomAmenityRepository.findByIdAndIsDeletedIsFalse(e).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND)));
        });
        property.setInRoomAmenities(amenities);

//        Map<Long,String> listOldImage = new HashMap<>();
//        dtoRequest.getListImageOld().forEach(e -> {
//            listOldImage.put(e,"image");
//        });
//        propertyImageService.gets(id).forEach(e -> {
//            if (!listOldImage.containsKey(e.getId())){
//                propertyImageService.delete(e.getId());
//            }
//        });

        //Delete image
        dtoRequest.getListImageDelete().forEach(propertyImageService::delete);
        //Create image
        if (propertyImages != null){
            propertyImages.forEach(e -> {
                propertyImageService.create(id, e);
            });
        }
        propertyRepository.save(property);
    }

    //update status
    @Override
    public void update(Long id, PropertyStatus propertyStatus) {
        var property = propertyRepository.findPropertyByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        property.setStatus(propertyStatus);
        propertyRepository.save(property);
    }

    @Override
    public void delete(Long id) {
        var propertyFound = propertyRepository.findPropertyByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        propertyFound.setIsDeleted(true);
        propertyRepository.save(propertyFound);
    }
}
