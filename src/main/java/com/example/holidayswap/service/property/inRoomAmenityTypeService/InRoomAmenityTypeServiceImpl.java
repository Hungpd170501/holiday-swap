package com.example.holidayswap.service.property.inRoomAmenityTypeService;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenityType;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.inRoomAmenity.InRoomAmenityTypeMapper;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityTypeRepository;
import com.example.holidayswap.service.property.inRoomAmenityService.InRoomAmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InRoomAmenityTypeServiceImpl implements InRoomAmenityTypeService {
    private final InRoomAmenityTypeRepository inRoomAmenityTypeRepository;

    private final InRoomAmenityService inRoomAmenityService;

    @Override
    public Page<InRoomAmenityTypeResponse> gets(String searchName, Pageable pageable) {
        Page<InRoomAmenityType> inRoomAmenityTypePage = inRoomAmenityTypeRepository.findAllByInRoomAmenityTypeNameContainingIgnoreCaseAndIsDeletedIsFalse(searchName, pageable);
        return inRoomAmenityTypePage.map(element -> {
            var dtoResponse = InRoomAmenityTypeMapper.INSTANCE.toDtoResponse(element);
            dtoResponse.setInRoomAmenities(inRoomAmenityService.getsByInRoomAmenityTypeId(element.getId()));
            return dtoResponse;
        });
    }

    @Override
    public List<InRoomAmenityTypeResponse> gets(Long propertyId) {
        List<InRoomAmenityType> inRoomAmenityTypes = inRoomAmenityTypeRepository.findInRoomAmenityTypesByPropertyIdAndDeletedFalse(propertyId);
        return inRoomAmenityTypes.stream().map(element -> {
            var dtoResponse = InRoomAmenityTypeMapper.INSTANCE.toDtoResponse(element);
            dtoResponse.setInRoomAmenities(inRoomAmenityService.gets(propertyId, element.getId()));
            return dtoResponse;
        }).toList();
    }

    @Override
    public InRoomAmenityTypeResponse get(Long id) {
        var inRoomAmenityTypeFound = inRoomAmenityTypeRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE_NOT_FOUND));
        var dtoResponse = InRoomAmenityTypeMapper.INSTANCE.toDtoResponse(inRoomAmenityTypeFound);
        dtoResponse.setInRoomAmenities(inRoomAmenityService.getsByInRoomAmenityTypeId(inRoomAmenityTypeFound.getId()));
        return dtoResponse;
    }

    @Override
    public InRoomAmenityTypeResponse create(InRoomAmenityTypeRequest inRoomAmenityTypeRequest) {
        var inRoomAmenityType = InRoomAmenityTypeMapper.INSTANCE.toEntity(inRoomAmenityTypeRequest);
        var inRoomAmenityTypeNew = inRoomAmenityTypeRepository.save(inRoomAmenityType);
        return InRoomAmenityTypeMapper.INSTANCE.toDtoResponse(inRoomAmenityTypeNew);
    }

    @Override
    public InRoomAmenityTypeResponse update(Long id, InRoomAmenityTypeRequest inRoomAmenityTypeRequest) {
        var inRoomAmenityTypeFound = inRoomAmenityTypeRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE_NOT_FOUND));
        InRoomAmenityTypeMapper.INSTANCE.updateEntityFromDTO(inRoomAmenityTypeRequest, inRoomAmenityTypeFound);
        inRoomAmenityTypeRepository.save(inRoomAmenityTypeFound);
        return InRoomAmenityTypeMapper.INSTANCE.toDtoResponse(inRoomAmenityTypeFound);
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityTypeFound = inRoomAmenityTypeRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE_NOT_FOUND));
        inRoomAmenityTypeFound.setIsDeleted(true);
        inRoomAmenityTypeRepository.save(inRoomAmenityTypeFound);
    }
}
