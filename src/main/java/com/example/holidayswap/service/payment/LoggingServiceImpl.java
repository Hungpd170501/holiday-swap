package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.AllLog;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.repository.payment.AllLogRepository;
import com.example.holidayswap.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoggingServiceImpl implements ILoggingService {
    @Autowired
    private AllLogRepository allLogRepo;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(long fromID, long toID, Long amount, EnumPaymentStatus.BankCodeError resultCode, String detail, int fromBalance, int toBalance) {
        AllLog allLog = new AllLog();
        allLog.setAmount(amount);
        allLog.setDetail(detail);
        allLog.setCreatedOn(Helper.getCurrentDate());
        allLog.setFromId(fromID);
        allLog.setToId(toID);
        allLog.setResultCode(resultCode);
        allLog.setFromBalance(fromBalance);
        allLog.setToBalance(toBalance);
        allLogRepo.save(allLog);
    }
}
