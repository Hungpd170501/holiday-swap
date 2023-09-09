package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
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
    @Transactional
    public void TransactionTopUpWallet(TopUpWalletDTO topUpWalletDTO, boolean status) {
        try {
            if(status != true) throw new Exception();
            moneyTranferService.CreateMoneyTranferTransaction(topUpWalletDTO,status);
            walletService.TopUpWallet(Long.parseLong(topUpWalletDTO.getUserId()) ,topUpWalletDTO.getAmount());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            moneyTranferService.CreateMoneyTranferTransaction(topUpWalletDTO,false);
        }

    }
}
