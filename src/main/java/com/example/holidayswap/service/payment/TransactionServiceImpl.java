package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.payment.StatusPayment;
import com.example.holidayswap.service.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IMoneyTranferService moneyTranferService;
    @Autowired
    private  IWalletService walletService;

    @Override
    public boolean TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, StatusPayment status, Long moneyTranferId) {
        try {
            walletService.TopUpWallet(Long.parseLong(topUpWalletDTO.getUserId()) ,10);
            moneyTranferService.UpdateStatusMoneyTranferTransaction(moneyTranferId,status);
            return true;
        }catch (BankException e){
            moneyTranferService.UpdateStatusMoneyTranferTransaction(moneyTranferId,StatusPayment.FAILED);
            e.printStackTrace();
            return false;
        }

    }
}
