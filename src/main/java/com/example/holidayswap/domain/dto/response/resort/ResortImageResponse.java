package com.example.holidayswap.domain.dto.response.resort;

import lombok.Data;

@Data
public class ResortImageResponse {
    private Long id;
    private Long resortId;
    private String link;
    private boolean isDeleted;
}