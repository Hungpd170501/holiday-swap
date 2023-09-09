package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.payment.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MoneyTranferServiceImpl implements IMoneyTranferService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public MoneyTranfer CreateMoneyTranferTransaction(TopUpWalletDTO topUpWalletDTO,boolean status) {

        MoneyTranfer moneyTranfer = new MoneyTranfer();
        User user = userRepository.findById(Long.parseLong(topUpWalletDTO.getUserId())).get();
        moneyTranfer.setAmount(topUpWalletDTO.getAmount());
        moneyTranfer.setBankCode(topUpWalletDTO.getBankCode());
        moneyTranfer.setStatus(status);
        moneyTranfer.setUser(user);
        moneyTranfer.setOrderInfor(topUpWalletDTO.getOrderInfor());

        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    try {
        Date date = sourceFormat.parse(topUpWalletDTO.getPaymentDate());
        String formattedString = targetFormat.format(date);
        moneyTranfer.setPaymentDate(formattedString);
    }catch (Exception e){
        e.printStackTrace();
    }
        transactionRepository.save(moneyTranfer);

        return moneyTranfer;
    }
}
