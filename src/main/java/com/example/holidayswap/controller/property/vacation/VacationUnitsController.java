package com.example.holidayswap.controller.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationUnitRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationUnitResponse;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import com.example.holidayswap.service.property.vacation.VacationUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vacation-units")
public class VacationUnitsController {
    private final VacationUnitService vacationUnitService;

    @GetMapping("/properties")
    public ResponseEntity<Page<VacationUnitResponse>> getAllByPropertyId(
            @RequestParam Long propertyId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var vacationResponses = vacationUnitService.getAllByPropertyId(propertyId, pageable);
        return ResponseEntity.ok(vacationResponses);
    }

    @GetMapping("/resorts")
    public ResponseEntity<Page<VacationUnitResponse>> getAllByResortId(
            @RequestParam Long resortId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var vacationResponses = vacationUnitService.getAllByResortId(resortId, pageable);
        return ResponseEntity.ok(vacationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationUnitResponse> get(
            @PathVariable("id") Long id) {
        var vacationResponse = vacationUnitService.get(id);
        return ResponseEntity.ok(vacationResponse);
    }

    @PostMapping
    public ResponseEntity<VacationUnitResponse> create(
            @RequestPart OwnershipId ownershipId,
            @RequestPart VacationUnitRequest dtoRequest
    ) {
        var vacation = vacationUnitService.create(ownershipId, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vacation.getId())
                .toUri();
        return ResponseEntity.created(location).body(vacation);
    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Void> update(@PathVariable Long id,
//                                       @RequestBody VacationUnitRequest dtoRequest) {
//        vacationUnitService.update(id, dtoRequest);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(id)
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vacationUnitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
