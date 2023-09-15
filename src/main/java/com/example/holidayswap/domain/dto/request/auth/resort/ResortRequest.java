package com.example.holidayswap.domain.dto.request.auth.resort;

import com.example.holidayswap.domain.entity.address.Address;
import com.example.holidayswap.domain.entity.property.ResortImage;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ResortRequest {
    private String resortName;
    private Address address;
    private Set<ResortImage> resortImages = new LinkedHashSet<>();
}
