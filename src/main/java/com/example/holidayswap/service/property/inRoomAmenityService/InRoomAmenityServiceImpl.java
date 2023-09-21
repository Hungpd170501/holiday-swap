package com.example.holidayswap.service.property.inRoomAmenityService;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenity;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.inRoomAmenity.InRoomAmenityMapper;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InRoomAmenityServiceImpl implements InRoomAmenityService {
    private final InRoomAmenityRepository inRoomAmenityRepository;

    @Override
    public Page<InRoomAmenityResponse> gets(String searchName, Long idInRoomAmenityType, Pageable pageable) {
        Page<InRoomAmenity> inRoomAmenityPage = inRoomAmenityRepository.findInRoomAmenitiesByInRoomAmenitiesNameContainingIgnoreCaseAndAndInRoomAmenitiesTypeIdAndIsDeletedIsFalse(searchName, idInRoomAmenityType, pageable);
        return inRoomAmenityPage.map(InRoomAmenityMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public List<InRoomAmenityResponse> gets(Long properId) {
        List<InRoomAmenity> inRoomAmenities = inRoomAmenityRepository.findInRoomAmenitiesByPropertyId(properId);
        return inRoomAmenities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public List<InRoomAmenityResponse> gets(Long propertyId, Long inRoomAmenityTypeId) {
        List<InRoomAmenity> inRoomAmenities = inRoomAmenityRepository.findInRoomAmenitiesByPropertyIdAndInRoomAmenityTypeId(propertyId, inRoomAmenityTypeId);
        return inRoomAmenities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public List<InRoomAmenityResponse> getsByInRoomAmenityTypeId(Long inRoomAmenityTypeId) {
        List<InRoomAmenity> inRoomAmenities = inRoomAmenityRepository.findAllByInRoomAmenityTypeIdAndIsDeletedFalse(inRoomAmenityTypeId);
        return inRoomAmenities.stream().map(InRoomAmenityMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public InRoomAmenityResponse get(Long id) {
        var inRoomAmenityFound = inRoomAmenityRepository.findInRoomAmenityByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND));
        var inRoomAmenityResponse = InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityFound);
        return inRoomAmenityResponse;
    }

    @Override
    public InRoomAmenityResponse create(InRoomAmenityRequest inRoomAmenityRequest) {
        var inRoomAmenity = InRoomAmenityMapper.INSTANCE.toEntity(inRoomAmenityRequest);
        var inRoomAmenityNew = inRoomAmenityRepository.save(inRoomAmenity);
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityNew);
    }

    @Override
    public InRoomAmenityResponse update(Long id, InRoomAmenityRequest inRoomAmenityRequest) {
        var inRoomAmenityFound = inRoomAmenityRepository.findInRoomAmenityByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND));
        InRoomAmenityMapper.INSTANCE.updateEntityFromDTO(inRoomAmenityRequest, inRoomAmenityFound);
        inRoomAmenityRepository.save(inRoomAmenityFound);
        return InRoomAmenityMapper.INSTANCE.toDtoResponse(inRoomAmenityFound);
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityFound = inRoomAmenityRepository.findInRoomAmenityByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_NOT_FOUND));
        inRoomAmenityFound.setIsDeleted(true);
        inRoomAmenityRepository.save(inRoomAmenityFound);
    }
}
