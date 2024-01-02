package com.example.holidayswap.domain.dto.request.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class CoOwnerRequest {
    private Long propertyId;
    private Long userId;
    private String roomId;
    private LocalDate startTime;
    private LocalDate endTime;
    private ContractType type;
    @NotNull
    @NotEmpty(message = "Time frames can not be null!.")
    private Set<
            @Min(1)
            @Max(value = 52, message = "Max week is 52")
            @NotNull
                    Integer> timeFrames;
}
