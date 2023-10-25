package com.example.holidayswap.controller.address;

import com.example.holidayswap.domain.dto.request.address.LocationRequest;
import com.example.holidayswap.domain.dto.response.address.LocationResponse;
import com.example.holidayswap.service.address.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/locations")
public class LocationController {
    private final LocationService locationService;

    @PutMapping("resort/{resortId}")
    public ResponseEntity<Void> updateLocation(@PathVariable Long resortId, @RequestBody LocationRequest locationRequest) {
        locationService.updateLocation(resortId, locationRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{resortId}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long resortId) {
        return ResponseEntity.ok(locationService.getLocation(resortId));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<LocationResponse>> getLocationList(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(locationService.getLocationList(ids));
    }
}
