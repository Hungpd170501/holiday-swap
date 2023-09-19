package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.request.payment.TransferRequest;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.service.payment.IWalletService;
import com.example.holidayswap.utils.helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private Condition condition ;

    @PostMapping
    public ResponseEntity<TransferResponse> transferPoint(@RequestBody TransferRequest request) {

        ReentrantLock fromWalletLock = walletLocks.get(walletService.GetWalletByUserId(request.getFrom()).getId());
        TransferResponse result = null;
//        condition = fromWalletLock.newCondition();
        if(fromWalletLock.tryLock()) {
        fromWalletLock.lock();
//            if(fromWalletLock.getHoldCount() > 1) {
//                condition.await();
//            }

        try {

                try {
                    Thread.sleep(3000);
                    result = transferPointService.transferPoint(request.getFrom(), request.getTo(), request.getAmount());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
//                condition.signal();
                fromWalletLock.unlock();
            }
        }
        return result == null ? ResponseEntity.badRequest().body( new TransferResponse(EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH,"fail", helper.getCurrentDate())) : ResponseEntity.ok(result);
    }

}
