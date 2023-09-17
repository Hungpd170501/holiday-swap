package com.example.holidayswap.service.property.inRoomAmenityTypeService;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.domain.entity.property.inRoomAmenity.InRoomAmenityType;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.inRoomAmenity.InRoomAmenityTypeMapper;
import com.example.holidayswap.repository.property.inRoomAmenity.InRoomAmenityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.IN_ROOM_AMENITY_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InRoomAmenityTypeServiceImpl implements InRoomAmenityTypeService {
    private final InRoomAmenityTypeRepository inRoomAmenityTypeRepository;
    private final InRoomAmenityTypeMapper inRoomAmenityTypeMapper;

    @Override
    public Page<InRoomAmenityTypeResponse> gets(String name, Pageable pageable) {
        Page<InRoomAmenityType> inRoomAmenityTypePage = inRoomAmenityTypeRepository.
                findAllByInRoomAmenityTypeNameContainingIgnoreCaseAndIsDeletedIsFalse(name, pageable);
        Page<InRoomAmenityTypeResponse> inRoomAmenityTypeResponsePage = inRoomAmenityTypePage.map(inRoomAmenityTypeMapper::toInRoomAmenityTypeResponse);
        return inRoomAmenityTypeResponsePage;
    }

    @Override
    public InRoomAmenityTypeResponse get(Long id) {
        var inRoomAmenityTypeFound = inRoomAmenityTypeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE_NOT_FOUND));
        var inRoomAmenityTypeResponse = inRoomAmenityTypeMapper.toInRoomAmenityTypeResponse(inRoomAmenityTypeFound);
        return inRoomAmenityTypeResponse;
    }

    @Override
    public InRoomAmenityTypeResponse create(InRoomAmenityTypeRequest inRoomAmenityTypeRequest) {
        var inRoomAmenityType = inRoomAmenityTypeMapper.toInRoomAmenityType(inRoomAmenityTypeRequest);
        var inRoomAmenityTypeNew = inRoomAmenityTypeRepository.save(inRoomAmenityType);
        return inRoomAmenityTypeMapper.toInRoomAmenityTypeResponse(inRoomAmenityTypeNew);
    }

    @Override
    public InRoomAmenityTypeResponse update(Long id, InRoomAmenityTypeRequest inRoomAmenityTypeRequest) {
        var inRoomAmenityTypeFound = inRoomAmenityTypeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE_NOT_FOUND));
        inRoomAmenityTypeMapper.updateEntityFromDTO(inRoomAmenityTypeRequest, inRoomAmenityTypeFound);
        inRoomAmenityTypeRepository.save(inRoomAmenityTypeFound);
        return inRoomAmenityTypeMapper.toInRoomAmenityTypeResponse(inRoomAmenityTypeFound);
    }

    @Override
    public void delete(Long id) {
        var inRoomAmenityTypeFound = inRoomAmenityTypeRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(IN_ROOM_AMENITY_TYPE_NOT_FOUND));
        inRoomAmenityTypeFound.setIsDeleted(true);
        inRoomAmenityTypeRepository.save(inRoomAmenityTypeFound);
    }
}
