package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationUnitRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationUnitResponse;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import com.example.holidayswap.domain.entity.property.vacation.VacationStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.vacation.VacationUnitMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.ownership.OwnershipRepository;
import com.example.holidayswap.repository.property.vacation.VacationUnitRepository;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.property.PropertyService;
import com.example.holidayswap.service.property.ownership.OwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.VACATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class VacationServiceUnitImpl implements VacationUnitService {
    private final VacationUnitRepository vacationRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OwnershipRepository ownershipRepository;
    private final UserService userService;
    private final PropertyService propertyService;
    private final OwnershipService ownershipService;

    @Override
    public Page<VacationUnitResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var dtoResponses = vacationRepository.findAllByPropertyId(propertyId, pageable)
                .map((e) -> {
                    var dto = VacationUnitMapper.INSTANCE.toDtoResponse(e);
                    return dto;
                });
        return dtoResponses;
    }

    @Override
    public Page<VacationUnitResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var dtoResponses = vacationRepository.findAllByResortId(resortId, pageable)
                .map((e) -> {
                    var dto = VacationUnitMapper.INSTANCE.toDtoResponse(e);
                    return dto;
                });
        return dtoResponses;
    }

    @Override
    public VacationUnitResponse get(Long id) {
        var vacationFound = vacationRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow();
        var vacationResponse = VacationUnitMapper.INSTANCE.toDtoResponse(vacationFound);
        return vacationResponse;
    }

    @Override
    public VacationUnitResponse create(OwnershipId ownershipId, VacationUnitRequest dtoRequest) {
        var entity = VacationUnitMapper.INSTANCE.toEntity(dtoRequest);

        entity.setOwnershipId(ownershipId.getOwnershipId());
        entity.setPropertyId(ownershipId.getPropertyId());
        entity.setUserId(ownershipId.getUserId());
        entity.setStatus(VacationStatus.PENDING);
        var created = vacationRepository.save(entity);
        return VacationUnitMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    public VacationUnitResponse update(Long id, VacationUnitRequest vacationRequest) {
//        var vacationFound = vacationRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow();
//        VacationUnitMapper.INSTANCE.updateEntityFromDTO(vacationRequest, vacationFound);
//        var vacationCreated = vacationRepository.save(vacationFound);
//        return VacationUnitMapper.INSTANCE.toDtoResponse(vacationCreated);
        return null;
    }

    @Override
    public void delete(Long id) {
        var vacationFound = vacationRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(VACATION_NOT_FOUND));
        vacationFound.setDeleted(true);
        vacationRepository.save(vacationFound);
    }
}
