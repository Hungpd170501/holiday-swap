package com.example.holidayswap.controller.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.service.resort.ResortImageService;
import com.example.holidayswap.service.resort.ResortService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resorts")
public class ResortsController {
    final private ResortService resortService;
    final private ResortImageService resortImageService;
    final private ResortAmenityTypeService resortAmenityTypeService;
    final private ResortAmenityService resortAmenityService;

    @GetMapping
    public ResponseEntity<Page<ResortResponse>> gets(
            @RequestParam(defaultValue = "") String nameResort,
            @RequestParam(value = "timeCheckIn", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date timeCheckIn,
            @RequestParam(value = "timeCheckOut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date timeCheckOut,
            @RequestParam(defaultValue = "0") Integer numberGuests,
            @RequestParam(value = "resortAmenity", required = false) Set<Long> listOfResortAmenity,
            @RequestParam(value = "inRoomAmenity", required = false) Set<Long> listOfInRoomAmenity,
            @RequestParam(value = "status", required = false) ResortStatus resortStatus,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(resortService.gets(nameResort, timeCheckIn, timeCheckOut, numberGuests, listOfResortAmenity, listOfInRoomAmenity, resortStatus, pageable));
    }

    @GetMapping("/{resortId}/resort-images")
    public ResponseEntity<List<ResortImageResponse>> getResortImages(
            @PathVariable("resortId") Long resortId) {
        var dtoResponses = resortImageService.gets(resortId);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{resortId}/resort-amenity-types")
    public ResponseEntity<List<ResortAmenityTypeResponse>> getResortAmenityTypes(
            @PathVariable("resortId") Long resortId) {
        return ResponseEntity.ok(resortAmenityTypeService.gets(resortId));
    }

    @GetMapping("/{resortId}/resort-amenity-types/{amenityTypeId}/resort-amenities")
    public ResponseEntity<List<ResortAmenityResponse>> gets(
            @PathVariable("resortId") Long resortId,
            @PathVariable("amenityTypeId") Long amenityId) {
        return ResponseEntity.ok(resortAmenityService.gets(amenityId, resortId));
    }

    @GetMapping("/{resortId}")
    public ResponseEntity<ResortResponse> get(
            @PathVariable("resortId") Long resortId) {
        return ResponseEntity.ok(resortService.get(resortId));
    }

    @PostMapping
    public ResponseEntity<ResortResponse> create(
            @RequestPart(name = "resort") ResortRequest resortRequest,
            @RequestPart List<MultipartFile> resortImage
    ) {
        var dtoResponse = resortService.create(resortRequest, resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{resortId}")
    public ResponseEntity<Void> update(@PathVariable("resortId") Long resortId,
                                       @RequestBody ResortRequest resortRequest) {
        resortService.update(resortId, resortRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{resortId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable("resortId") Long resortId,
                                             @RequestBody ResortStatus resortStatus) {
        resortService.update(resortId, resortStatus);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{resortId}")
    public ResponseEntity<Void> delete(@PathVariable("resortId") Long resortId) {
        resortService.delete(resortId);
        return ResponseEntity.noContent().build();
    }
}
