package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.payment.TopUpWalletDTO;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.domain.entity.payment.StatusPayment;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.payment.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class MoneyTranferServiceImpl implements IMoneyTranferService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public MoneyTranfer CreateMoneyTranferTransaction(TopUpWalletDTO topUpWalletDTO, StatusPayment status) {

        if(status.name() != StatusPayment.WAITING.name()) return null;

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

    @Override
    public MoneyTranfer GetMoneyTranferTransaction(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public boolean UpdateStatusMoneyTranferTransaction(Long id, StatusPayment status) {
        Optional<MoneyTranfer> moneyTranfer = transactionRepository.findById(id);
        if(!moneyTranfer.isPresent() || moneyTranfer.get().getStatus().name() != StatusPayment.WAITING.name()){
            return false;
        }
        MoneyTranfer moneyTranfer1 = moneyTranfer.get();
        moneyTranfer1.setStatus(status);
        transactionRepository.save(moneyTranfer1);
        return true;
    }
}
