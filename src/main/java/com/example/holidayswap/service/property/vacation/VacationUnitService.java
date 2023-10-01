package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationUnitRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationUnitResponse;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationUnitService {
    Page<VacationUnitResponse> getAllByPropertyId(Long propertyId, Pageable pageable);

    Page<VacationUnitResponse> getAllByResortId(Long resortId, Pageable pageable);

    VacationUnitResponse get(Long id);

    VacationUnitResponse create(OwnershipId ownershipId, VacationUnitRequest dtoRequest);

    VacationUnitResponse update(Long id, VacationUnitRequest dtoRequest);

    void delete(Long id);
}
