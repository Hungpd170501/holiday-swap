package com.example.holidayswap.service.address;

import com.example.holidayswap.domain.dto.request.address.LocationRequest;
import com.example.holidayswap.domain.dto.response.address.LocationResponse;

import java.util.List;

public interface LocationService {

    void updateLocation(Long resortId, LocationRequest locationRequest);

    LocationResponse getLocation(Long resortId);

    List<LocationResponse> getLocationList(List<Long> ids);
}