package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.request.payment.TransferRequest;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.service.payment.IWalletService;
import com.example.holidayswap.utils.helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.holidayswap.service.payment.WalletServiceImpl.walletLocks;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferPointController {

    @Autowired
    private ITransferPointService transferPointService;
    @Autowired
    private IWalletService walletService;


    @PostMapping
    public ResponseEntity<TransferResponse> transferPoint(@RequestBody TransferRequest request) {

        ReentrantLock fromWalletLock = walletLocks.get(walletService.GetWalletByUserId(request.getFrom()).getId());
        TransferResponse result = null;
        if (fromWalletLock.tryLock()) {
            fromWalletLock.lock();
            try {

                try {
                    Thread.sleep(3000);
                    result = transferPointService.transferPoint(request.getFrom(), request.getTo(), request.getAmount());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                fromWalletLock.unlock();
            }
        }
        return result == null ? ResponseEntity.badRequest().body(new TransferResponse(EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, "fail", helper.getCurrentDate())) : ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> getTransactionTranferPointByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(transferPointService.getTransactionTranferPointByUserId(userId));
    }

}
