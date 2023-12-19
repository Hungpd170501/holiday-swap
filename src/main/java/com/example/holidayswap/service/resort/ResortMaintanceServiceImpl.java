package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.entity.resort.ResortMaintance;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.domain.exception.ResourceNotFoundException;
import com.example.holidayswap.repository.resort.ResortMaintanceRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ResortMaintanceServiceImpl implements IResortMaintanceService {

    private final ResortMaintanceRepository resortMaintanceRepository;
    private final ResortRepository resortRepository;

    @Override
    public void CreateResortMaintance(Long resortId, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus) {
        var checkResort = resortRepository.findById(resortId).orElseThrow(() -> new RuntimeException("Resort not found"));
        var checkIsDeactivate = resortMaintanceRepository.findAllByTypeAndResortId(ResortStatus.DEACTIVATE,resortId);
        ResortMaintance checkIsMaintance = null;
        checkIsMaintance = resortMaintanceRepository.findByResortIdAndStartDateAndEndDateAndType(resortId, startDate, startDate, ResortStatus.MAINTENANCE);
        if(checkIsDeactivate.size() > 0) {
            throw new RuntimeException("Resort is deactivated");
        }
        if(resortStatus != ResortStatus.DEACTIVATE) {
            checkIsMaintance = resortMaintanceRepository.findByResortIdAndStartDateAndEndDateAndType(resortId, startDate, endDate, ResortStatus.MAINTENANCE);
        }
        if(checkIsMaintance != null) {
            throw new RuntimeException("Resort is already in maintenance");
        }

        ResortMaintance resortMaintance = new ResortMaintance();

        resortMaintance.setStartDate(startDate);
            resortMaintance.setEndDate(endDate);
            resortMaintance.setType(resortStatus);
            resortMaintance.setResortId(resortId);
            resortMaintance.setResort(checkResort);
            resortMaintanceRepository.save(resortMaintance);
    }

    @Override
    public void ChangeStatusResortAtStartDate(Long resortId, LocalDateTime startDate) {
        var checkResort = resortRepository.findById(resortId).orElseThrow(() -> new RuntimeException("Resort not found"));
        var checkIsDeactivate = resortMaintanceRepository.findByResortIdAndType(resortId,ResortStatus.DEACTIVATE);
        var checkIsMaintance = resortMaintanceRepository.findByResortIdAndStartDateAndEndDateAndType(resortId, startDate, startDate, ResortStatus.MAINTENANCE);

        if(checkIsDeactivate != null && checkIsDeactivate.getStartDate().isEqual(startDate)) {
            checkResort.setStatus(ResortStatus.DEACTIVATE);
        } else if (checkIsMaintance != null && checkIsMaintance.getStartDate().isEqual(startDate)) {
            checkResort.setStatus(ResortStatus.MAINTENANCE);
        }else return;
        resortRepository.save(checkResort);
    }

    @Override
    public void ChangeStatusResortAtEndDate(Long resortId, LocalDateTime endDate) {
        var checkResort = resortRepository.findById(resortId).orElseThrow(() -> new RuntimeException("Resort not found"));
        var checkIsMaintance = resortMaintanceRepository.findByResortIdAndStartDateAndEndDateAndType(resortId, endDate, endDate, ResortStatus.MAINTENANCE);
        var checkIsDeactivate = resortMaintanceRepository.findByResortIdAndType(resortId,ResortStatus.DEACTIVATE);

         if (checkIsMaintance != null && checkIsMaintance.getEndDate().isEqual(endDate) && !checkIsDeactivate.getStartDate().isBefore(endDate)) {
            checkResort.setStatus(ResortStatus.ACTIVE);
        }else return;
        resortRepository.save(checkResort);
    }


}
