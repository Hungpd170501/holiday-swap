package com.example.holidayswap.domain.dto.request.subscription;

import com.example.holidayswap.domain.entity.subscription.PlanPriceInterval;
import com.example.holidayswap.domain.entity.subscription.PriceType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlanRequest {
    private String planName;
    private String description;
    private MultipartFile image;
    private double price;
    private PriceType priceType;
    private PlanPriceInterval planPriceInterval;
}
