package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.service.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private IMoneyTranferService moneyTranferService;
    @Autowired
    private  IWalletService walletService;

    @Override
    public boolean TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, boolean status) {
        try {
            walletService.TopUpWallet(Long.parseLong(topUpWalletDTO.getUserId()) ,10);
            moneyTranferService.CreateMoneyTranferTransaction(topUpWalletDTO,status);
            return true;
        }catch (BankException e){
            moneyTranferService.CreateMoneyTranferTransaction(topUpWalletDTO,false);
            e.printStackTrace();
            return false;
        }

    }
}
