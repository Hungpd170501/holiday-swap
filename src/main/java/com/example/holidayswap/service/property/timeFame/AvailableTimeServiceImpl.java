package com.example.holidayswap.service.property.timeFame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.timeFrame.AvailableTimeMapper;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.property.timeFrame.TimeFrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.VACATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AvailableTimeServiceImpl implements AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;
    private final TimeFrameRepository timeFrameRepository;

    @Override
    public Page<AvailableTimeResponse> getAllByVacationUnitId(Long vacationUnitId, Pageable pageable) {
        var timeOffDepositPage = availableTimeRepository.findAllByVacationUnitIdAndDeletedIsFalse(vacationUnitId, pageable);
        Page<AvailableTimeResponse> timeOffDepositPageResponse = timeOffDepositPage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var timeOffDepositPage = availableTimeRepository.
                findAllByPropertyIdAndDeletedFalse(propertyId, pageable);
        Page<AvailableTimeResponse> timeOffDepositPageResponse = timeOffDepositPage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public Page<AvailableTimeResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var timeOffDepositPage = availableTimeRepository.
                findAllByResortIdAndDeletedFalse(resortId, pageable);
        Page<AvailableTimeResponse> timeOffDepositPageResponse = timeOffDepositPage.map(AvailableTimeMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public AvailableTimeResponse get(Long id) {
        var timeOffDepositFound = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow();
        var timeOffDepositResponse = AvailableTimeMapper.INSTANCE.toDtoResponse(timeOffDepositFound);
        return timeOffDepositResponse;
    }

    @Override
    public AvailableTimeResponse create(Long vacationUnitId, AvailableTimeRequest dtoRequest) {
        if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        var vacationUnitFound = timeFrameRepository.
                findByIdAndIsDeletedIsFalse(vacationUnitId).orElseThrow(() -> new EntityNotFoundException(VACATION_NOT_FOUND));
        //check is in vacation unit time
        var checkIsInVacationUnitTime = timeFrameRepository.
                findByStartTimeAndEndTimeIsInVacationUnitTime(
                        vacationUnitFound.getPropertyId(),
                        vacationUnitFound.getUserId(),
                        vacationUnitFound.getRoomId(),
                        dtoRequest.getStartTime(),
                        dtoRequest.getEndTime(),
                        TimeFrameStatus.ACCEPTED.toString()
                );
        if (checkIsInVacationUnitTime.isEmpty())
            throw new DataIntegrityViolationException("Your public time is not in range vacation unit");
        var checkDuplicateWhichAnyTimeDeposit = availableTimeRepository.findOverlapsWhichAnyTimeDeposit(
                vacationUnitId,
                dtoRequest.getStartTime(),
                dtoRequest.getEndTime(),
                AvailableTimeStatus.OPEN
        );
        if (checkDuplicateWhichAnyTimeDeposit.isPresent())
            throw new DataIntegrityViolationException("Duplicate with another time off deposit");
        var timeOffDeposit = AvailableTimeMapper.INSTANCE.toEntity(dtoRequest);
        timeOffDeposit.setStatus(AvailableTimeStatus.OPEN);
        timeOffDeposit.setTimeFrameId(vacationUnitId);
        var timeOffDepositCreated = availableTimeRepository.save(timeOffDeposit);
        return AvailableTimeMapper.INSTANCE.toDtoResponse(timeOffDepositCreated);
    }

    @Override
    public AvailableTimeResponse update(Long id, AvailableTimeRequest timeOffDepositRequest) {
        var timeOffDeposit = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow();
        AvailableTimeMapper.INSTANCE.updateEntityFromDTO(timeOffDepositRequest, timeOffDeposit);
        var timeOffDepositCreated = availableTimeRepository.save(timeOffDeposit);
        return AvailableTimeMapper.INSTANCE.toDtoResponse(timeOffDepositCreated);
    }

    @Override
    public void delete(Long id) {
        var timeOffDeposit = availableTimeRepository.findByIdAndDeletedFalse(id).orElseThrow();
        timeOffDeposit.setDeleted(true);
        availableTimeRepository.save(timeOffDeposit);
    }
}
