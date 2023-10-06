package com.example.holidayswap.service.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.OwnershipResponse;
import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.ContractType;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ownership.OwnershipMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;

import com.example.holidayswap.repository.property.ownership.OwnershipRepository;
import com.example.holidayswap.service.property.vacation.VacationUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OwnershipServiceImpl implements OwnershipService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OwnershipRepository ownershipRepository;
    private final ContractImageService contractImageService;
    private final VacationUnitService vacationUnitService;

    @Override
    public List<OwnershipResponse> getListByPropertyId(Long propertyId) {
        var dtoResponse = ownershipRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId).
                stream().map(OwnershipMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponse;
    }

    @Override
    public List<OwnershipResponse> getListByUserId(Long userId) {
        var dtoResponse = ownershipRepository.findAllByUserIdAndIsDeletedIsFalse(userId).
                stream().map(OwnershipMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponse;
    }

    @Override
    public OwnershipResponse get(OwnershipId ownershipId) {
        var dtoResponse = OwnershipMapper.INSTANCE.toDtoResponse(
                ownershipRepository.findById(ownershipId).
                        orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND)));
        dtoResponse.setContractImages(contractImageService.gets(
                dtoResponse.getId().getPropertyId(),
                dtoResponse.getId().getUserId()));
        return dtoResponse;
    }

    @Override
    public OwnershipResponse create(Long propertyId,
                                    Long userId,
                                    OwnershipRequest dtoRequest) {
        if (dtoRequest.getType() == ContractType.DEEDED) {
            dtoRequest.setStartTime(null);
            dtoRequest.setEndTime(null);
        } else if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");

        var entity = OwnershipMapper.INSTANCE.toEntity(dtoRequest);
        var property = propertyRepository.findPropertyById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND));


        var id = new OwnershipId();
        id.setPropertyId(propertyId);
        id.setUserId(userId);
        id.setRoomId(dtoRequest.getRoomId());
        entity.setId(id);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(ContractStatus.PENDING);
        var created = ownershipRepository.save(entity);
        dtoRequest.getVacations().forEach(e -> {
            vacationUnitService.create(id, e);
        });
        return OwnershipMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    public OwnershipResponse create(Long propertyId,
                                    Long userId,
                                    OwnershipRequest dtoRequest,
                                    List<MultipartFile> contractImages) {
//
//        // get id user is login
//        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        User user = (User) principal;
        var dtoResponse = create(propertyId, userId, dtoRequest);
        contractImages.forEach(e -> {
            ContractImageRequest c = new ContractImageRequest();
            c.setPropertyId(propertyId);
            c.setUserId(userId);
            contractImageService.create(c, e);
        });
        return dtoResponse;
    }

    @Override
    public OwnershipResponse update(Long propertyId, Long userId) {
        return null;
    }

    @Override
    public void delete(Long propertyId, Long userId) {
        var entity = ownershipRepository.findAllByPropertyIdAndUserIdAndIsDeleteIsFalse(propertyId, userId).
                orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND));
        entity.setDeleted(true);
        ownershipRepository.save(entity);
    }
}
