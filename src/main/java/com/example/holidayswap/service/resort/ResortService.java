package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.request.resort.ResortUpdateRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ResortService {
    Page<ResortResponse> gets(String locationName, String name, Set<Long> listOfResortAmenity, ResortStatus resortStatus, Pageable pageable);

    ResortResponse get(Long id);

    ResortResponse create(ResortRequest resortRequest);

    ResortResponse create(ResortRequest resortRequest, List<MultipartFile> resortImage);

    void update(Long id, ResortUpdateRequest resortRequest, List<MultipartFile> resortImage);

    void update(Long id, ResortStatus resortStatus);


    void delete(Long id, LocalDate startDate);

    void updateStatus(Long id, ResortStatus resortStatus, LocalDate startDate, LocalDate endDate);
}
