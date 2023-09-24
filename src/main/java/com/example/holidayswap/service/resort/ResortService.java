package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResortService {
    Page<ResortResponse> gets(Pageable pageable);

    ResortResponse get(Long id);

    ResortResponse create(ResortRequest resortRequest);

    ResortResponse update(Long id, ResortRequest resortRequest);

    void delete(Long id);
}
