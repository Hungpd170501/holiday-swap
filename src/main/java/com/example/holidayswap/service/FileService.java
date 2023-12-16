package com.example.holidayswap.service;

import com.google.zxing.WriterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws IOException;
    String createQRCode(String link) throws IOException, WriterException;
}
