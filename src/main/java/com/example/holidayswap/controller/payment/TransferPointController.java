package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.request.payment.TransferRequest;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.service.payment.ITransferPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferPointController {

    @Autowired
    private ITransferPointService transferPointService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferPoint(@RequestBody TransferRequest request) {
        TransferResponse result = transferPointService.transferPoint(request.getFrom(), request.getTo(), request.getAmount());
        return  ResponseEntity.ok(result);
    }

}
