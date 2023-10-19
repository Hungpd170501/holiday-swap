package com.example.holidayswap.domain.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Long roleId;
    private String name;
    @JsonProperty("status")
    private boolean status;
}
