package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.auth.RoleName;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.PropertyType;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.entity.resort.Resort;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.ResortMapper;
import com.example.holidayswap.repository.property.PropertyTypeRespository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityRepository;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.property.PropertyService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {
    private final ResortRepository resortRepository;
    private final ResortImageService resortImageService;
    private final ResortAmenityTypeService resortAmenityTypeService;
    private final ResortAmenityRepository resortAmenityRepository;
    private final PropertyTypeRespository propertyTypeRespository;
    private final PropertyService propertyService;
    private final UserService userService;

    @Override
    public Page<ResortResponse> gets(String name,
                                     Date timeCheckIn,
                                     Date timeCheckOut,
                                     int numberGuests,
                                     Set<Long> listOfResortAmenity,
                                     Set<Long> listOfInRoomAmenity,
                                     ResortStatus resortStatus,
                                     Pageable pageable) {

        // return par
        Page<Resort> entities = null;
        //Get user
        Optional<User> user = userService.getUser();
        //If staff, admin search all
        if (user.isPresent() &&
            (Objects.equals(user.get().getRole().getName(), RoleName.Staff.toString())
             || Objects.equals(user.get().getRole().getName(), RoleName.Admin.toString()))) {
            entities = resortRepository.findAllByFilter(name, listOfResortAmenity, resortStatus, pageable);
        } else {
            resortStatus = ResortStatus.ACTIVE;
            //default get
            PropertyStatus propertyStatus = PropertyStatus.ACTIVE;
            CoOwnerStatus coOwnerStatus = CoOwnerStatus.ACCEPTED;
            TimeFrameStatus timeFrameStatus = TimeFrameStatus.ACCEPTED;
            AvailableTimeStatus availableTimeStatus = AvailableTimeStatus.OPEN;
            entities = resortRepository.findAllByFilter(name,
                    timeCheckIn,
                    timeCheckOut,
                    numberGuests,
                    listOfResortAmenity,
                    listOfInRoomAmenity,
                    resortStatus,
                    propertyStatus,
                    coOwnerStatus,
                    timeFrameStatus,
                    availableTimeStatus,
                    pageable);
        }
        var dtoReponses = entities.map(e -> {
            var dto = ResortMapper.INSTANCE.toResortResponse(e);
            dto.setResortImages(resortImageService.gets(e.getId()));
            dto.setResortAmenityTypes(resortAmenityTypeService.gets(e.getId()));
            return dto;
        });
        return dtoReponses;
    }

    @Override
    public ResortResponse get(Long id) {
        var entity = resortRepository.findByIdAndDeletedFalseAndResortStatus(id, ResortStatus.ACTIVE).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        var dtoResponse = ResortMapper.INSTANCE.toResortResponse(entity);

        dtoResponse.setResortImages(resortImageService.gets(id));
        dtoResponse.setResortAmenityTypes(resortAmenityTypeService.gets(entity.getId()));

        return dtoResponse;
    }

    @Override
    @Transactional
    public ResortResponse create(ResortRequest resortRequest) {
        if (resortRepository.findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(resortRequest.getResortName()).
                isPresent())
            throw new DuplicateRecordException(DUPLICATE_RESORT_NAME);
        var entity = ResortMapper.INSTANCE.toResort(resortRequest);
        entity.setStatus(ResortStatus.ACTIVE);
        List<ResortAmenity> resortAmenities = new ArrayList<>();
        resortRequest.getAmenities().forEach(e -> {
            resortAmenities.add(resortAmenityRepository.findByIdAndIsDeletedFalse(e).orElseThrow(
                    () -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND)));
        });
        List<PropertyType> propertyTypes = new ArrayList<>();
        resortRequest.getPropertyTypes().forEach(e -> {
            propertyTypes.add(propertyTypeRespository.findByIdAndIsDeletedFalse(e).orElseThrow(
                    () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND)));
        });
        entity.setAmenities(resortAmenities);
        entity.setPropertyTypes(propertyTypes);
        Long id = resortRepository.save(entity).getId();
        return get(id);
    }

    @Override
    @Transactional
    public ResortResponse create(ResortRequest resortRequest, List<MultipartFile> resortImage) {
        var entity = create(resortRequest);
        resortImage.forEach(e -> {
            resortImageService.create(entity.getId(), e);
        });
        return get(entity.getId());
    }

    @Override
    public ResortResponse update(Long id, ResortRequest resortRequest) {
        var entityFound = resortRepository.
                findByResortNameEqualsIgnoreCaseAndIsDeletedFalse(resortRequest.getResortName());
        if (entityFound.isPresent() && !Objects.equals(entityFound.get().getId(), id)) {
            throw new DuplicateRecordException(DUPLICATE_RESORT_NAME);
        }
        var entity = resortRepository.findByIdAndDeletedFalseAndResortStatus(id, ResortStatus.ACTIVE).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        List<ResortAmenity> resortAmenities = new ArrayList<>();
        resortRequest.getAmenities().forEach(e -> {
            resortAmenities.add(resortAmenityRepository.findByIdAndIsDeletedFalse(e).orElseThrow(
                    () -> new EntityNotFoundException(RESORT_AMENITY_NOT_FOUND)));
        });
        List<PropertyType> propertyTypes = new ArrayList<>();
        resortRequest.getPropertyTypes().forEach(e -> {
            propertyTypes.add(propertyTypeRespository.findByIdAndIsDeletedFalse(e).orElseThrow(
                    () -> new EntityNotFoundException(PROPERTY_TYPE_NOT_FOUND)));
        });
        ResortMapper.INSTANCE.updateEntityFromDTO(resortRequest, entity);
        entity.setAmenities(resortAmenities);
        entity.setPropertyTypes(propertyTypes);
        Long i = resortRepository.save(entity).getId();
        return get(i);
    }

    @Override
    public ResortResponse update(Long id, ResortStatus resortStatus) {
        var entity = resortRepository.findByIdAndDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        entity.setStatus(resortStatus);
        Long i = resortRepository.save(entity).getId();
        return get(i);
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityTypeFound = resortRepository.findByIdAndDeletedFalseAndResortStatus(id, ResortStatus.ACTIVE).
                orElseThrow(() -> new EntityNotFoundException(RESORT_NOT_FOUND));
        inRoomAmenityTypeFound.setDeleted(true);
        resortRepository.save(inRoomAmenityTypeFound);
    }
}
