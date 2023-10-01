package com.example.holidayswap.service.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationResponse;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import com.example.holidayswap.domain.entity.property.vacation.Vacation;
import com.example.holidayswap.domain.entity.property.vacation.VacationStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.vacation.VacationMapper;
import com.example.holidayswap.repository.property.vacation.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

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
    @Transactional
    public VacationResponse create(Ownership ownership, VacationRequest vacationRequest) {
        var vaction = VacationMapper.INSTANCE.toEntity(vacationRequest);
        var vacationListOfThisApartment = vacationRepository.findAllByPropertyIdAndRoomId(ownership.getId().getPropertyId(),ownership.getId().getRoomId());

        if(vacationListOfThisApartment.size() >0){
            for (Vacation v : vacationListOfThisApartment) {
                if(v.getStartTime().before(vaction.getStartTime())
                        && v.getEndTime().after(vaction.getEndTime())) {
                    throw new EntityNotFoundException("This time is not available");
                }
                if(vaction.getStartTime().before(v.getStartTime())
                        && v.getStartTime().before(vaction.getEndTime())) {
                    throw new EntityNotFoundException("This time is not available");
                }
                if(vaction.getStartTime().before(v.getEndTime())
                        && v.getEndTime().before(vaction.getEndTime())) {
                    throw new EntityNotFoundException("This time is not available");
                }
                if(vaction.getStartTime().before(v.getStartTime())
                        && v.getEndTime().before(vaction.getEndTime())) {
                    throw new EntityNotFoundException("This time is not available");
                }if(vaction.getStartTime().compareTo(v.getStartTime()) == 0
                        && v.getEndTime().compareTo(vaction.getEndTime()) ==0)  {
                    throw new EntityNotFoundException("This time is not available");
                }
            }
        }

        vaction.setPropertyId(ownership.getId().getPropertyId());
        vaction.setRoomId(ownership.getId().getRoomId());
        vaction.setUserId(ownership.getId().getUserId());
        vaction.setStatus(VacationStatus.PENDING);
        vaction.setDeleted(false);
        vaction.setOwnershipVacation(ownership);

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
