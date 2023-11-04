package com.example.holidayswap.domain.dto.request.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    private String addressLine;
    private String locationFormattedName;
    private String locationDescription;
    private String locationCode;
    private String postalCode;
    private Float latitude;
    private Float longitude;
    private DistrictRequest district;
    private StateOrProvinceRequest stateOrProvince;
    private CountryRequest country;
}
