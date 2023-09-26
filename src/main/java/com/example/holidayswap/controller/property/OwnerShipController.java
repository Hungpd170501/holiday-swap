package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.CreateOwnerShipRequest;
import com.example.holidayswap.domain.entity.property.Ownership;
import com.example.holidayswap.service.property.IOwnerShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ownership")
public class OwnerShipController {
    @Autowired
    private IOwnerShipService ownerShipService;
    @PostMapping
    public ResponseEntity<?> create(@RequestPart CreateOwnerShipRequest request, @RequestPart  List<MultipartFile> contractImages) {
            Ownership ownership = ownerShipService.create(request.getPropertyId(),request.getStartDate(),request.getEndDate(),contractImages);
            return ResponseEntity.ok(ownership);
    }

}
