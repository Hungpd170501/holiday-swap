package com.example.holidayswap.service.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.OwnershipResponse;
import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ownership.OwnershipMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.ownership.OwnershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OwnershipServiceImpl implements OwnershipService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OwnershipRepository ownershipRepository;
    private final ContractImageService contractImageService;

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
    public OwnershipResponse get(Long propertyId, Long userId) {
        var dtoResponse = OwnershipMapper.INSTANCE.toDtoResponse(
                ownershipRepository.findAllByPropertyIdAndUserIdAndIsDeleteIsFalse(propertyId, userId).
                        orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND)));
        dtoResponse.setContractImages(contractImageService.gets(propertyId, userId));
        return dtoResponse;
    }

    @Override
    public OwnershipResponse create(Long propertyId,
                                    Long userId,
                                    OwnershipRequest dtoRequest) {
//        get(propertyId, userId);
        var entity = OwnershipMapper.INSTANCE.toEntity(dtoRequest);
        var property = propertyRepository.findPropertyById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND));
        var id = new OwnershipId();
        UUID uuid = UUID.randomUUID();
        id.setOwnershipId(uuid.toString());
        id.setPropertyId(propertyId);
        id.setUserId(userId);
        entity.setId(id);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(ContractStatus.PENDING);
        return OwnershipMapper.INSTANCE.toDtoResponse(
                ownershipRepository.save(entity));
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
