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
//        var vacations = vacationRepository.findAllByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(propertyId, vacationRequest.getStartTime(), vacationRequest.getEndTime());
//        var vacationPresent = vacationRepository.findByPropertyIdAndDeletedIsFalseAndAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(propertyId, vacationRequest.getStartTime(), vacationRequest.getEndTime());
//        if (vacationPresent.isPresent()) throw new EntityNotFoundException("da ton tai 1 ban ghi ");
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(vacationRequest.getStartTime());

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(vacationRequest.getEndTime());
        if(calEnd.get(Calendar.MONTH) < calStart.get(Calendar.MONTH)){
            throw new EntityNotFoundException("enddate can not less than startdate");
        }
        if(calEnd.get(Calendar.MONTH) == calStart.get(Calendar.MONTH)
                && calEnd.get(Calendar.DAY_OF_MONTH) < calStart.get(Calendar.DAY_OF_MONTH)){
            throw new EntityNotFoundException("enddate can not less than startdate");
        }
//        List<Vacation> vacationsOfOwnerShip = vacationRepository.findAllByPropertyIdAndUserIdAndRoomId(ownership.getId().getPropertyId(), ownership.getId().getUserId(), ownership.getId().getRoomId());
//        vacationsOfOwnerShip.stream().forEach(e -> {
//            Calendar calStart1 = Calendar.getInstance();
//            calStart1.setTime(e.getStartTime());
//
//            Calendar calEnd1 = Calendar.getInstance();
//            calEnd1.setTime(e.getEndTime());
//            if(calStart1.get(Calendar.MONTH) > calStart.get(Calendar.MONTH) && calStart.get(Calendar.MONTH) < calEnd1.get(Calendar.MONTH)){
//                throw new EntityNotFoundException("This time coincides with the previously created time period ");
//            }
//            if(calStart1.get(Calendar.MONTH) > calEnd.get(Calendar.MONTH) && calEnd.get(Calendar.MONTH) < calEnd1.get(Calendar.MONTH)){
//                throw new EntityNotFoundException("This time coincides with the previously created time period ");
//            }
//            if(calStart1.get(Calendar.MONTH) == calStart.get(Calendar.MONTH)
//                    && calEnd1.get(Calendar.MONTH) == calStart.get(Calendar.MONTH)
//                    && calStart1.get(Calendar.DAY_OF_MONTH) > calStart.get(Calendar.DAY_OF_MONTH)
//                    && calStart.get(Calendar.DAY_OF_MONTH) < calEnd1.get(Calendar.DAY_OF_MONTH)){
//                throw new EntityNotFoundException("This time coincides with the previously created time period ");
//            }
//            if(calStart1.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
//                    && calEnd1.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
//                    && calStart1.get(Calendar.DAY_OF_MONTH) > calEnd.get(Calendar.DAY_OF_MONTH)
//                    && calEnd.get(Calendar.DAY_OF_MONTH) < calEnd1.get(Calendar.DAY_OF_MONTH)){
//                throw new EntityNotFoundException("This time coincides with the previously created time period ");
//            }
//        });
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
