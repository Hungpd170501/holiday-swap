package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrame;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.TimeFrameMapper;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.OWNERSHIP_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.VACATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TimeFrameServiceUnitImpl implements TimeFrameService {
    private final TimeFrameRepository timeFrameRepository;
    private final CoOwnerRepository coOwnerRepository;

    @Override
    public Page<TimeFrameResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var dtoResponses = timeFrameRepository.findAllByPropertyId(propertyId, pageable)
                .map((e) -> {
                    var dto = TimeFrameMapper.INSTANCE.toDtoResponse(e);
                    return dto;
                });
        return dtoResponses;
    }

    @Override
    public Page<TimeFrameResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var dtoResponses = timeFrameRepository.findAllByResortId(resortId, pageable)
                .map((e) -> {
                    var dto = TimeFrameMapper.INSTANCE.toDtoResponse(e);
                    return dto;
                });
        return dtoResponses;
    }

    @Override
    public TimeFrameResponse get(Long id) {
        var vacationFound = timeFrameRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow();
        var vacationResponse = TimeFrameMapper.INSTANCE.toDtoResponse(vacationFound);
        return vacationResponse;
    }

    @Override
    @Transactional
    public TimeFrameResponse create(CoOwnerId ownershipId, TimeFrameRequest dtoRequest) {
        var entity = TimeFrameMapper.INSTANCE.toEntity(dtoRequest);
        //Check begin time and end time is format
        if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        //Check if type is deeded, check overlap?
        //find ownership
        var ownership = coOwnerRepository.findByPropertyIdAndUserUserIdAndIdRoomId(
                        ownershipId.getPropertyId()
                        , ownershipId.getUserId()
                        , ownershipId.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND));
        //Declare list
        List<TimeFrame> checkVacationUnit;
        //Check overlaps with any

        checkVacationUnit = timeFrameRepository.
                findOverlapWith(
                        ownershipId.getPropertyId(),
                        ownershipId.getUserId(),
                        ownershipId.getRoomId(),
                        dtoRequest.getStartTime(),
                        dtoRequest.getEndTime(),
                        TimeFrameStatus.ACCEPTED.toString(),
                        CoOwnerStatus.ACCEPTED.toString()
                );
        if (!checkVacationUnit.isEmpty()) {
            if (ownership.getType() == ContractType.DEEDED)
                throw new DuplicateRecordException("Overlaps ownership type DEEDED");
            else checkVacationUnit.forEach(e -> {
                var ownershipCheck = coOwnerRepository.
                        checkOverlapsTimeOwnership(
                                ownershipId.getPropertyId(),
                                ownershipId.getUserId(),
                                ownershipId.getRoomId(),
                                ownership.getStartTime(),
                                ownership.getEndTime());
                if (ownershipCheck.isPresent())
                    if (ownershipCheck.get().getType() == ContractType.DEEDED)
                        throw new DuplicateRecordException("Overlaps ownership type DEEDED");
                    else throw new DuplicateRecordException("Overlaps ownership type RIGHT TO USE");
            });
        }
//        Check user only create 1 request of each property
        //kiem tra xem cai request nay da co chua, co roi thi khong dc tao them, chi duoc 1 request duy nhat, tr
        var checkVacationIsCreated = timeFrameRepository.
                findByStartTimeAndEndTimeIsInVacationUnitTime(
                        ownershipId.getPropertyId(),
                        ownershipId.getUserId(),
                        ownershipId.getRoomId(),
                        dtoRequest.getStartTime(),
                        dtoRequest.getEndTime(),
                        TimeFrameStatus.PENDING.toString()
                );
        if (checkVacationIsCreated.isPresent())
            throw new DuplicateRecordException("Only 1 request for 1 person in 1 property");
        //pass all then create
        entity.setPropertyId(ownershipId.getPropertyId());
        entity.setUserId(ownershipId.getUserId());
        entity.setRoomId(ownershipId.getRoomId());
        entity.setStatus(TimeFrameStatus.PENDING);
        var created = timeFrameRepository.save(entity);
        return TimeFrameMapper.INSTANCE.toDtoResponse(created);
    }

    @Override
    public TimeFrameResponse update(Long id, TimeFrameRequest vacationRequest) {
//        var vacationFound = timeFrameRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow();
//        VacationUnitMapper.INSTANCE.updateEntityFromDTO(vacationRequest, vacationFound);
//        var vacationCreated = timeFrameRepository.save(vacationFound);
//        return VacationUnitMapper.INSTANCE.toDtoResponse(vacationCreated);
        return null;
    }

    @Override
    public void delete(Long id) {
        var vacationFound = timeFrameRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(VACATION_NOT_FOUND));
        vacationFound.setDeleted(true);
        timeFrameRepository.save(vacationFound);
    }
}
