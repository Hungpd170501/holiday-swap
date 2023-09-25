package com.example.holidayswap.domain.dto.request.resort;

import lombok.Data;

@Data
public class ResortRequest {
    private String resortName;
    private Long locationId;
}
