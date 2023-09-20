package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.request.payment.TransferRequest;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.service.payment.IWalletService;
import com.example.holidayswap.utils.RedissonLockUtils;
import com.example.holidayswap.utils.helper;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferPointController {

    @Autowired
    private ITransferPointService transferPointService;
    @Autowired
    private IWalletService walletService;

    @PostMapping
    public ResponseEntity<TransferResponse> transferPoint(@RequestBody TransferRequest request) throws InterruptedException {
        RLock fairLock = RedissonLockUtils.getFairLock("wallet-" + (walletService.GetWalletByUserId(request.getFrom()).getId()).toString());
        TransferResponse result = null;
        boolean tryLock = fairLock.tryLock(10,10, TimeUnit.SECONDS);
        if (tryLock){
            try {
                fairLock.lock();
                Thread.sleep(3000);
                result = transferPointService.transferPoint(request.getFrom(), request.getTo(), request.getAmount());
            } finally {
                fairLock.unlock();
            }
        }
        return result == null ? ResponseEntity.badRequest().body(new TransferResponse(EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, "fail", helper.getCurrentDate())) : ResponseEntity.ok(result);
    }

}
