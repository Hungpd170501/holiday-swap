package com.example.holidayswap.controller.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.service.property.coOwner.CoOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/co-owners")
public class CoOwnersController {
    private final CoOwnerService coOwnerService;

    @GetMapping("/resort/{resortId}")
    public ResponseEntity<Page<CoOwnerResponse>> getByResortId(@RequestParam("resortId") Long resortId,
                                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                               @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(coOwnerService.getByResortId(resortId, pageable));
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<Page<CoOwnerResponse>> getByPropertyId(@RequestParam("propertyId") Long propertyId,
                                                                 @RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(coOwnerService.getByPropertyId(propertyId, pageable));
    }

    @GetMapping("/users/{userId}/properties/{propertyId}")
    public ResponseEntity<Page<CoOwnerResponse>> getCoOwnerBelongToUser(@PathVariable("userId") Long userId,
                                                                        @PathVariable("propertyId") Long propertyId,
                                                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                                        @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(coOwnerService.getCoOwnerBelongToUser(userId, propertyId, pageable));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<CoOwnerResponse>> getResortUserOwn(@PathVariable("userId") Long userId,
                                                                 @RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(coOwnerService.getCoOwnerByUserId(userId, pageable));
    }

    @GetMapping
    public ResponseEntity<CoOwnerResponse> get(
            @RequestParam Long propertyId,
            @RequestParam Long userId,
            @RequestParam String roomId) {
        return ResponseEntity.ok(coOwnerService.get(propertyId, userId, roomId));
    }

    @PostMapping
    public ResponseEntity<CoOwnerResponse> create(@RequestPart("coOwnerId") CoOwnerId coOwnerId,
                                                  @RequestPart("coOwner") CoOwnerRequest dtoRequest,
                                                  @RequestPart List<MultipartFile> contractImages) {
        return ResponseEntity.ok(coOwnerService.create(coOwnerId, dtoRequest, contractImages));
    }

    @PutMapping
    public ResponseEntity<CoOwnerResponse> update(@RequestPart("coOwnerId") CoOwnerId coOwnerId,
                                                  @RequestPart("coOwnerStatus") CoOwnerStatus coOwnerStatus) {
        return ResponseEntity.ok(coOwnerService.update(coOwnerId, coOwnerStatus));
    }

    @PutMapping("/status")
    public ResponseEntity<CoOwnerResponse> updateStatus(
            @RequestPart("coOwnerId") CoOwnerId coOwnerId,
            @RequestPart("coOwnerStatus") CoOwnerStatus coOwnerStatus) {
        return ResponseEntity.ok(coOwnerService.update(coOwnerId, coOwnerStatus));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestPart("coOwnerId") CoOwnerId coOwnerId) {

        coOwnerService.delete(coOwnerId);
        return ResponseEntity.noContent().build();
    }
}
