package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TransferPointServiceImpl implements ITransferPointService{

//    @Autowired
//    private IWalletService walletService;

    @Override
    @Transactional
    public TransferResponse transferPoint(long from, long to, long amount) {
        Wallet fromWallet ;
        Wallet toWallet ;

//        try {
//            fromWallet = walletService.GetWalletByUserId(from);
//            toWallet = walletService.GetWalletByUserId(to);
//        } catch (AccountException accountException){
//            loggingService.saveLog(fromAccID, toAccID, amount, accountException.getErrorCode(), accountException.getMessage());
//            throw new BankException(accountException.getErrorCode(), "Account Error", accountException.getMessage());
//        }
        return null;
    }
}
