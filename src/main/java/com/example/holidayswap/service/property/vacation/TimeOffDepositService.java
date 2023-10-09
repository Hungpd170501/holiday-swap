package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.TimeOffDepositRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.TimeOffDepositResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOffDepositService {
    Page<TimeOffDepositResponse> getAllByVacationUnitId(Long vacationId, Pageable pageable);

    //    Page<TimeOffDepositResponse> getAllBy(Long propertyId, Pageable pageable);
    Page<TimeOffDepositResponse> getAllByPropertyId(Long propertyId, Pageable pageable);

    Page<TimeOffDepositResponse> getAllByResortId(Long resortId, Pageable pageable);

    TimeOffDepositResponse get(Long id);

    TimeOffDepositResponse create(Long vacationId, TimeOffDepositRequest timeOffDepositRequest);

    TimeOffDepositResponse update(Long id, TimeOffDepositRequest timeOffDepositRequest);

    void delete(Long id);
}
