package com.example.holidayswap.domain.dto.response.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResDTO {
    private String status;
    private String message;
    private String URL;
}
