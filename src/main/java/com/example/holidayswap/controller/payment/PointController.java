package com.example.holidayswap.controller.payment;

import com.example.holidayswap.service.payment.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/point")
public class PointController {

    @Autowired
    private IPointService pointService;

    @PostMapping("/create")
    public void CreateNewPointPrice(Double price){
        pointService.CreateNewPointPrice(price);
    }
    @GetMapping
    public ResponseEntity<?> GetActivePoint(){
        return ResponseEntity.ok(pointService.GetActivePoint());
    }
}
