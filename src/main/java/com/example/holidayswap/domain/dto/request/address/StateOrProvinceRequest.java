package com.example.holidayswap.domain.dto.request.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateOrProvinceRequest {
    private String name;
    private String code;
    private String type;
}
