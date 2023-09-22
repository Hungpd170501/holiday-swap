package com.example.holidayswap.domain.dto.response.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
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
    private String amount;
    private double totalPoint;
    private String status;
    private String date;
    private EnumPaymentStatus.StatusPointTransfer statusPointTransfer;
    private Double commission;
}
