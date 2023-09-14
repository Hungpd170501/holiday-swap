package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.entity.payment.Point;
import com.example.holidayswap.service.BankException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IMoneyTranferService moneyTranferService;
    @Autowired
    private  IWalletService walletService;

    @Autowired
    private IPointService pointService;

    @Override
    @Transactional
    public boolean TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, EnumPaymentStatus.StatusMoneyTranfer status, Long moneyTranferId) {
        try {
            Point  point = pointService.GetActivePoint();
            walletService.TopUpWallet(Long.parseLong(topUpWalletDTO.getUserId()) , (int) (topUpWalletDTO.getAmount() / point.getPointPrice()));
            moneyTranferService.UpdateStatusMoneyTranferTransaction(moneyTranferId,status);
            return true;
        }catch (BankException e){
            log.error("BankException occurred during top-up wallet: {}", e.getMessage(), e);
            moneyTranferService.UpdateStatusMoneyTranferTransaction(moneyTranferId, EnumPaymentStatus.StatusMoneyTranfer.FAILED);
            return false;
        }

    }
}
