package com.example.holidayswap.domain.dto.request.chat;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageRequest {
    private String text;
    private MultipartFile image;
    private Long authorId;
}
