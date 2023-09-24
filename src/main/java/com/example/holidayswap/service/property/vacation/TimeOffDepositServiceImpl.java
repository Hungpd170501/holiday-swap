package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.TimeOffDepositRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.TimeOffDepositResponse;
import com.example.holidayswap.domain.mapper.property.vacation.TimeOffDepositMapper;
import com.example.holidayswap.repository.property.vacation.TimeOffDepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeOffDepositServiceImpl implements TimeOffDepositService {
    private final TimeOffDepositRepository timeOffDepositRepository;

    @Override
    public Page<TimeOffDepositResponse> gets(Long vacationId, Pageable pageable) {
        var timeOffDepositPage = timeOffDepositRepository.findAllByVacationIdAndDeletedIsFalse(vacationId, pageable);
        Page<TimeOffDepositResponse> timeOffDepositPageResponse = timeOffDepositPage.map(TimeOffDepositMapper.INSTANCE::toDtoResponse);
        return timeOffDepositPageResponse;
    }

    @Override
    public Page<TimeOffDepositResponse> getByResortId(Long resortId, Pageable pageable) {
//        var timeOffDepositPage = timeOffDepositRepository.findAllByResortIdAndDeletedFalse(resortId, pageable);
//        Page<TimeOffDepositResponse> timeOffDepositPageResponse = timeOffDepositPage.map(TimeOffDepositMapper.INSTANCE::toDtoResponse);
//        return timeOffDepositPageResponse;
        return null;
    }

    @Override
    public TimeOffDepositResponse get(Long id) {
        var timeOffDepositFound = timeOffDepositRepository.findByIdAndDeletedFalse(id).orElseThrow();
        var timeOffDepositResponse = TimeOffDepositMapper.INSTANCE.toDtoResponse(timeOffDepositFound);
        return timeOffDepositResponse;
    }

    @Override
    public TimeOffDepositResponse create(Long vacationId, TimeOffDepositRequest timeOffDepositRequest) {
        var timeOffDeposit = TimeOffDepositMapper.INSTANCE.toEntity(timeOffDepositRequest);
        var timeOffDeposits = timeOffDepositRepository.findAllByVacationIdAndAndDeletedFalseAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(vacationId, timeOffDepositRequest.getStartTime(), timeOffDepositRequest.getEndTime());
//        if (!timeOffDeposits.isEmpty()) new Exception();
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
