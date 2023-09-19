package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.entity.payment.TransactLog;
import com.example.holidayswap.domain.entity.payment.Wallet;
import com.example.holidayswap.repository.payment.TransactLogRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import com.example.holidayswap.service.AccountException;
import com.example.holidayswap.service.BankException;
import com.example.holidayswap.utils.helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.holidayswap.service.payment.WalletServiceImpl.walletLocks;

@Service
public class TransferPointServiceImpl implements ITransferPointService{

    @Autowired
    private IWalletService walletService;

    @Autowired
    private ILoggingService loggingService;

    @Autowired
    private TransactLogRepository transactLogRepository;

    @Autowired
    private WalletRepository walletRepository;
    private static final Lock sharedLock = new ReentrantLock(true);

    @Override
    @Transactional(rollbackFor = { BankException.class })
    public TransferResponse transferPoint(long from, long to, long amount) {
        Wallet fromWallet ;
        Wallet toWallet ;
        String currentDate;

        try {
            fromWallet = walletService.GetWalletByUserId(from);
            toWallet = walletService.GetWalletByUserId(to);

        } catch (AccountException accountException){
            loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.ID_NOT_FOUND, accountException.getMessage());
            throw new BankException("Account Error");
        }


            if (fromWallet.getTotalPoint() < amount) {
                String detail = "Account " + fromWallet.getId() + " of user has id " + fromWallet.getUser().getUserId() + " does not have enough balance";
                loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, detail);
                throw new BankException(detail);
            }

            boolean check = fromWallet.withdraw(amount);
            if(!check){
                String detail = "Account " + fromWallet.getId() + " of user has id " + fromWallet.getUser().getUserId() + " does not have enough balance";
                loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, detail);
                throw new BankException(detail);
            }

            toWallet.setTotalPoint((int) (toWallet.getTotalPoint() + amount));
            currentDate = helper.getCurrentDate();
            TransactLog transactLog = new TransactLog();
            transactLog.setAmountPoint(amount);
            transactLog.setCreatedOn(currentDate);
            transactLog.setWalletFrom(fromWallet);
            transactLog.setWalletTo(toWallet);

            walletRepository.save(fromWallet);
            walletRepository.save(toWallet);
            transactLogRepository.save(transactLog);
            loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.SUCCESS, "Success");

        return new TransferResponse(EnumPaymentStatus.BankCodeError.SUCCESS, "Success",currentDate);
    }
}
