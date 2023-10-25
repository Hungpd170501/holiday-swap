package com.example.holidayswap.domain.dto.response.address;

import com.example.holidayswap.domain.entity.address.District;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long resortId;
    private String addressLine;
    private String locationFormattedName;
    private String locationDescription;
    private String locationCode;
    private Float latitude;
    private Float longitude;
    private District district;
    private String postalCode;
}
