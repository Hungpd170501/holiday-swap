package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.request.payment.TransferRequest;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.service.payment.ITransferPointService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static com.example.holidayswap.service.payment.WalletServiceImpl.walletLocks;

@RestController
@Log4j
public class TransferPointController {

    @Autowired
    private ITransferPointService transferPointService;

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferPoint(@RequestBody TransferRequest request) {
        ReentrantLock fromWalletLock = walletLocks.get(request.getFrom());
        TransferResponse result;
        try {
            fromWalletLock.lock();
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                Logger.getLogger(e.getMessage());
            }
            result = transferPointService.transferPoint(request.getFrom(), request.getTo(), request.getAmount());
        } finally {
            fromWalletLock.unlock();
        }
        return ResponseEntity.ok(result);
    }

}
