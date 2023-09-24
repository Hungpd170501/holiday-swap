package com.example.holidayswap.domain.dto.request.resort;

import com.example.holidayswap.domain.entity.resort.ResortImage;
import lombok.Data;

import java.util.List;

@Data
public class ResortRequest {
    private String resortName;
    private Long addressId;
    private List<ResortImage> resortImages;
}
