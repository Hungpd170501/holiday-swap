package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.request.payment.TransferRequest;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.service.payment.IWalletService;
import com.example.holidayswap.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfer")
public class TransferPointController {

    @Autowired
    private ITransferPointService transferPointService;
    @Autowired
    private IWalletService walletService;


    @GetMapping
    public ResponseEntity<?> getTransactionTranferPointByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(transferPointService.getTransactionTranferPointByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<TransferResponse> transferPoint(@RequestBody TransferRequest request) throws InterruptedException {
        TransferResponse result = null;
        if (request.getAmount() < 1) throw new DataIntegrityViolationException("Amount must be a integer value!.");
        result = transferPointService.transferPoint(request.getFrom(), request.getTo(), request.getAmount());
        return result == null ? ResponseEntity.badRequest().body(new TransferResponse(EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, "fail", Helper.getCurrentDate())) : ResponseEntity.ok(result);
    }

}