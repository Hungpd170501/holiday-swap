package com.example.holidayswap.domain.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTranferPointResponse {
    private Long id;
    private String from;
    private String to;
    private double amount;
    private double totalPoint;
    private String status;
    private String date;
}
