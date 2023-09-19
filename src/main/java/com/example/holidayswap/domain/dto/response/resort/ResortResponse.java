package com.example.holidayswap.domain.dto.response.resort;

import lombok.Data;

import java.util.List;

@Data
public class ResortResponse {
    private Long id;
    private String resortName;
    private Long address;
    private boolean isDeleted;
    private List<ResortImageResponse> resortImages;
}
