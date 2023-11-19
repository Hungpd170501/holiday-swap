package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResortImageService {

    List<ResortImageResponse> gets(Long resortId);

    ResortImageResponse get(Long id);

    ResortImageResponse create(Long resortId, MultipartFile multipartFile);

    ResortImageResponse update(Long Id, MultipartFile multipartFile);

    void setImageToResort(Long resortId, List<String> linkImages);

    void delete(Long id);
}
