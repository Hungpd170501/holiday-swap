package com.example.holidayswap.controller.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.VacationRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.VacationResponse;
import com.example.holidayswap.domain.mapper.property.vacation.VacationMapper;
import com.example.holidayswap.service.property.vacation.VacationService;
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
@RequestMapping("api/v1/vactions")
public class VacationsController {
    private final VacationService vacationService;

    @GetMapping
    public ResponseEntity<Page<VacationResponse>> gets(
            @RequestParam Long propertyId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var vacationResponses = vacationService.gets(propertyId, pageable);
        return ResponseEntity.ok(vacationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacationResponse> get(
            @PathVariable("id") Long id) {
        var vacationResponse = vacationService.get(id);
        return ResponseEntity.ok(vacationResponse);
    }

    @PostMapping(value = "/{id}")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VacationResponse> create(
            @PathVariable Long id,
            @RequestBody VacationRequest vacationRequest) {
        var vacation = vacationService.create(id, vacationRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vacation.getId())
                .toUri();
        return ResponseEntity.created(location).body(VacationMapper.INSTANCE.toDtoResponse(vacation));
    }

    @PutMapping(value = "/{id}")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody VacationRequest vacationRequest) {
        vacationService.update(id, vacationRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vacationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
