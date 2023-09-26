package com.example.holidayswap.domain.dto.request.resort;

import lombok.Data;

import java.util.List;

@Data
public class ResortRequest {
    private String resortName;
    private Long locationId;
    private List<Long> resortAmentities;
}
