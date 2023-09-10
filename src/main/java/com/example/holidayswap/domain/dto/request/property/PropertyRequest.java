package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.resort.Resort;
import lombok.Data;

@Data
public class PropertyRequest {
    private Long id;
    private User user;
    private Resort resort;
    //private PropertyType propertyType;
//    private String status;
//    private Boolean isdeleted;
}
