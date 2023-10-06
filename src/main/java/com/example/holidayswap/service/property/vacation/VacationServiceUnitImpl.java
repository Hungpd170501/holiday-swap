package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationUnitRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationUnitResponse;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import com.example.holidayswap.domain.entity.property.vacation.VacationStatus;
import com.example.holidayswap.domain.entity.property.vacation.VacationUnit;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.vacation.VacationUnitMapper;
import com.example.holidayswap.repository.property.vacation.VacationUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.VACATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class VacationServiceUnitImpl implements VacationUnitService {
    private final VacationUnitRepository vacationUnitRepository;

    @Override
    public Page<VacationUnitResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var dtoResponses = vacationUnitRepository.findAllByPropertyId(propertyId, pageable)
                .map((e) -> {
                    var dto = VacationUnitMapper.INSTANCE.toDtoResponse(e);
                    return dto;
                });
        return dtoResponses;
    }

    @Override
    public Page<VacationUnitResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var dtoResponses = vacationUnitRepository.findAllByResortId(resortId, pageable)
                .map((e) -> {
                    var dto = VacationUnitMapper.INSTANCE.toDtoResponse(e);
                    return dto;
                });
        return dtoResponses;
    }

    @Override
    public VacationUnitResponse get(Long id) {
        var vacationFound = vacationUnitRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow();
        var vacationResponse = VacationUnitMapper.INSTANCE.toDtoResponse(vacationFound);
        return vacationResponse;
    }

    @Override
    @Transactional
    public VacationUnitResponse create(OwnershipId ownershipId, VacationUnitRequest dtoRequest) {
        var entity = VacationUnitMapper.INSTANCE.toEntity(dtoRequest);
        //Check begin time and end time is format
        if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        //Check if type is deeded, check overlap?
        //find ownership
        //Declare list
        List<VacationUnit> checkVacationUnit;
        //Check overlaps with any
        {
            checkVacationUnit = vacationUnitRepository.
                    findByPropertyIdAndRoomIdAndStartTimeBetweenAndEndTimeBetweenAndDeletedIsFalseAndStatus(
                            ownershipId.getPropertyId(),
                            ownershipId.getRoomId(),
                            dtoRequest.getStartTime(),
                            dtoRequest.getEndTime(),
                            VacationStatus.ACCEPTED.toString()
                    );
            if (!checkVacationUnit.isEmpty()) throw new DuplicateRecordException("Overlaps time.");
        }
        //Check user only create 1 request of each property
        checkVacationUnit = vacationUnitRepository.
                findByPropertyIdAndRoomIdAndStartTimeBetweenAndEndTimeBetweenAndDeletedIsFalseAndStatus(
                        ownershipId.getPropertyId(),
                        ownershipId.getRoomId(),
                        dtoRequest.getStartTime(),
                        dtoRequest.getEndTime(),
                        VacationStatus.PENDING.toString());
        if (!checkVacationUnit.isEmpty())
            throw new DuplicateRecordException("Only 1 request for 1 person in 1 property");
        //pass all then create
        entity.setPropertyId(ownershipId.getPropertyId());
        entity.setUserId(ownershipId.getUserId());
        entity.setRoomId(ownershipId.getRoomId());
        entity.setStatus(VacationStatus.PENDING);
        var created = vacationUnitRepository.save(entity);
        return VacationUnitMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    public VacationUnitResponse update(Long id, VacationUnitRequest vacationRequest) {
//        var vacationFound = vacationUnitRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow();
//        VacationUnitMapper.INSTANCE.updateEntityFromDTO(vacationRequest, vacationFound);
//        var vacationCreated = vacationUnitRepository.save(vacationFound);
//        return VacationUnitMapper.INSTANCE.toDtoResponse(vacationCreated);
        return null;
    }

    @Override
    public void delete(Long id) {
        var vacationFound = vacationUnitRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(VACATION_NOT_FOUND));
        vacationFound.setDeleted(true);
        vacationUnitRepository.save(vacationFound);
    }
}
