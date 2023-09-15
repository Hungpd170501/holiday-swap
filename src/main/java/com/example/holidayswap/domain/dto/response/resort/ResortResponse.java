package com.example.holidayswap.domain.dto.response.resort;

import com.example.holidayswap.domain.entity.address.Address;
import com.example.holidayswap.domain.entity.property.ResortImage;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ResortResponse {
    private Long id;
    private String resortName;
    private Address address;
    private boolean isDeleted;
    private Set<ResortImage> resortImages = new LinkedHashSet<>();
}
