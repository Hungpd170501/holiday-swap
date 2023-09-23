package com.example.holidayswap.domain.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopUpWalletDTO {
    private String bankCode;
    private String orderInfor;
    private int amount;
    private String userId;
    private String paymentDate;

}
