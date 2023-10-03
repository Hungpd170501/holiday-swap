package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ResortService {
    Page<ResortResponse> gets(String name, Date timeCheckIn, Date timeCheckOut, Pageable pageable);

    ResortResponse get(Long id);

    ResortResponse create(ResortRequest resortRequest);

    ResortResponse create(ResortRequest resortRequest, List<MultipartFile> resortImage);

    ResortResponse update(Long id, ResortRequest resortRequest);

    void delete(Long id);
}
