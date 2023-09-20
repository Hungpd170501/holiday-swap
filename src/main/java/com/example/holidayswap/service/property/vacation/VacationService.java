package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationResponse;
import com.example.holidayswap.domain.entity.property.vacation.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationService {
    Page<VacationResponse> gets(Long propertyId, Pageable pageable);

    VacationResponse get(Long id);

    Vacation create(Long propertyId, VacationRequest vacationRequest);

    Vacation update(Long id, VacationRequest vacationRequest);

    void delete(Long id);
}
