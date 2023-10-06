package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.TimeOffDepositRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.TimeOffDepositResponse;
import com.example.holidayswap.domain.entity.property.vacation.TimeOffDepositStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.vacation.TimeOffDepositMapper;
import com.example.holidayswap.repository.property.vacation.TimeOffDepositRepository;
import com.example.holidayswap.repository.property.vacation.VacationUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.VACATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TimeOffDepositServiceImpl implements TimeOffDepositService {
    private final TimeOffDepositRepository timeOffDepositRepository;
    private final VacationUnitRepository vacationUnitRepository;

    @Override
    public Page<TimeOffDepositResponse> getAllByVacationUnitId(Long vacationUnitId, Pageable pageable) {
        var timeOffDepositPage = timeOffDepositRepository.findAllByVacationUnitIdAndDeletedIsFalse(vacationUnitId, pageable);
        Page<TimeOffDepositResponse> timeOffDepositPageResponse = timeOffDepositPage.map(TimeOffDepositMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public Page<TimeOffDepositResponse> getAllByPropertyId(Long propertyId, Pageable pageable) {
        var timeOffDepositPage = timeOffDepositRepository.
                findAllByPropertyIdAndDeletedFalse(propertyId, pageable);
        Page<TimeOffDepositResponse> timeOffDepositPageResponse = timeOffDepositPage.map(TimeOffDepositMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public Page<TimeOffDepositResponse> getAllByResortId(Long resortId, Pageable pageable) {
        var timeOffDepositPage = timeOffDepositRepository.
                findAllByResortIdAndDeletedFalse(resortId, pageable);
        Page<TimeOffDepositResponse> timeOffDepositPageResponse = timeOffDepositPage.map(TimeOffDepositMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public TimeOffDepositResponse get(Long id) {
        var timeOffDepositFound = timeOffDepositRepository.findByIdAndDeletedFalse(id).orElseThrow();
        var timeOffDepositResponse = TimeOffDepositMapper.INSTANCE.toDtoResponse(timeOffDepositFound);
        return timeOffDepositResponse;
    }

    @Override
    public TimeOffDepositResponse create(Long vacationUnitId, TimeOffDepositRequest dtoRequest) {
        if (dtoRequest.getStartTime().after(dtoRequest.getEndTime()))
            throw new DataIntegrityViolationException("Start time must be before end time");
        var vacationUnitFound = vacationUnitRepository.findByIdAndIsDeletedIsFalse(vacationUnitId);
        if (vacationUnitFound.isEmpty()) throw new EntityNotFoundException(VACATION_NOT_FOUND);
//        var checkIsInVacationUnitTime = vacationUnitRepository.
//                findByStartTimeAndEndTimeIsInVacationUnitTime(
//                        vacationUnitFound.get().getPropertyId(),
//                        vacationUnitFound.get().getRoomId(),
//                        dtoRequest.getStartTime(),
//                        dtoRequest.getEndTime(),
//                        VacationStatus.ACCEPTED
//                );
//        if (checkIsInVacationUnitTime.isEmpty())
//            throw new DataIntegrityViolationException("There are no vacation include this range time");
        var checkDuplicateWhichAnyTimeDeposit = timeOffDepositRepository.findOverlapsWhichAnyTimeDeposit(
                vacationUnitId,
                dtoRequest.getStartTime(),
                dtoRequest.getEndTime(),
                TimeOffDepositStatus.OPEN
        );
        if (checkDuplicateWhichAnyTimeDeposit.isPresent())
            throw new DataIntegrityViolationException("Duplicate with another time off deposit");
        var timeOffDeposit = TimeOffDepositMapper.INSTANCE.toEntity(dtoRequest);
        timeOffDeposit.setStatus(TimeOffDepositStatus.OPEN);
        var timeOffDepositCreated = timeOffDepositRepository.save(timeOffDeposit);
        return TimeOffDepositMapper.INSTANCE.toDtoResponse(timeOffDepositCreated);
    }

    @Override
    public TimeOffDepositResponse update(Long id, TimeOffDepositRequest timeOffDepositRequest) {
        var timeOffDeposit = timeOffDepositRepository.findByIdAndDeletedFalse(id).orElseThrow();
        TimeOffDepositMapper.INSTANCE.updateEntityFromDTO(timeOffDepositRequest, timeOffDeposit);
        var timeOffDepositCreated = timeOffDepositRepository.save(timeOffDeposit);
        return TimeOffDepositMapper.INSTANCE.toDtoResponse(timeOffDepositCreated);
    }

    @Override
    public void delete(Long id) {
        var timeOffDeposit = timeOffDepositRepository.findByIdAndDeletedFalse(id).orElseThrow();
        timeOffDeposit.setDeleted(true);
        timeOffDepositRepository.save(timeOffDeposit);
    }
}
