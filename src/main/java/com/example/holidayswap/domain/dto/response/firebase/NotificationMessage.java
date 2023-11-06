package com.example.holidayswap.domain.dto.response.firebase;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String,String> data;
}
