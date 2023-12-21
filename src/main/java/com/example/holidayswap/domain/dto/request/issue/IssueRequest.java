package com.example.holidayswap.domain.dto.request.issue;

import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import lombok.Data;

@Data
public class IssueRequest {
    private Long issueId;
    private String issueDescription;
    private EnumBookingStatus.IssueBookingStatus issueStatus;
}
