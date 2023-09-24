package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationResponse;
import com.example.holidayswap.domain.entity.property.vacation.VacationStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.vacation.VacationMapper;
import com.example.holidayswap.repository.property.vacation.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;

    @Override
    public Page<VacationResponse> gets(Long propertyId, Pageable pageable) {
        var vactionPage = vacationRepository.findAllByPropertyIdAndDeletedIsFalse(propertyId, pageable);
        return vactionPage.map(element -> {
            var toDtoResponse = VacationMapper.INSTANCE.toDtoResponse(element);
            toDtoResponse.setTimeOffDeposits(null);
            return toDtoResponse;
        });
    }

    @Override
    public VacationResponse get(Long id) {
        var vacationFound = vacationRepository.findByIdAndDeletedFalse(id).orElseThrow();
        var vacationResponse = VacationMapper.INSTANCE.toDtoResponse(vacationFound);
        return vacationResponse;
    }

    @Override
    public VacationResponse create(Long propertyId, VacationRequest vacationRequest) {
        var vaction = VacationMapper.INSTANCE.toEntity(vacationRequest);
        var vacations = vacationRepository.findAllByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(propertyId, vacationRequest.getStartTime(), vacationRequest.getEndTime());
        var vacationPresent = vacationRepository.findByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(propertyId, vacationRequest.getStartTime(), vacationRequest.getEndTime());
        if (vacationPresent.isPresent()) throw new EntityNotFoundException("da ton tai 1 ban ghi ");
        vaction.setStatus(VacationStatus.PENDING);
        vaction.setPropertyId(propertyId);
        var vacationCreated = vacationRepository.save(vaction);
        return VacationMapper.INSTANCE.toDtoResponse(vacationCreated);
    }

    @Override
    public VacationResponse update(Long id, VacationRequest vacationRequest) {
        var vacationFound = vacationRepository.findByIdAndDeletedFalse(id).orElseThrow();
        VacationMapper.INSTANCE.updateEntityFromDTO(vacationRequest, vacationFound);
        var vacationCreated = vacationRepository.save(vacationFound);
        return VacationMapper.INSTANCE.toDtoResponse(vacationCreated);
    }

    @Override
    public void delete(Long id) {
        var vacationFound = vacationRepository.findByIdAndDeletedFalse(id).orElseThrow();
        vacationFound.setDeleted(true);
        vacationRepository.save(vacationFound);
    }
}
