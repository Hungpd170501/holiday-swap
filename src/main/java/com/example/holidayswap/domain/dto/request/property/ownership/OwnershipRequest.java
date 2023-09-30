package com.example.holidayswap.domain.dto.request.property.ownership;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.entity.property.ownership.ContractType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OwnershipRequest {
    private Date endTime;
    private Date startTime;
    private ContractType type;
    private String roomId;
    private List<VacationRequest> vacations;

}
