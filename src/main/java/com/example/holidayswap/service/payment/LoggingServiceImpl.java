package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.AllLog;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.repository.payment.AllLogRepository;
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
    public void saveLog(long fromID, long toID, Long amount, EnumPaymentStatus.BankCodeError resultCode, String detail) {
        AllLog allLog = new AllLog();
        allLog.setAmount(amount);
        allLog.setDetail(detail);
        allLog.setCreatedOn("123");
        allLog.setFromID(fromID);
        allLog.setToID(toID);
        allLog.setResultCode(resultCode);
        allLogRepo.save(allLog);
    }
}
