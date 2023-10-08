package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.entity.payment.TransactionBookingRefundOwner;
import com.example.holidayswap.repository.payment.TransactionBookingRefundOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionBookingRefundOwnerServiceImpl implements ITransactionBookingRefundOwnerService{

    @Autowired
    private TransactionBookingRefundOwnerRepository transactionBookingRefundOwnerRepository;

    @Override
    public void saveLog(long fromBookingId, long toMemberId, Double amount, EnumPaymentStatus.BankCodeError resultCode, String detail, String createdOn, Double memberBalance) {
        TransactionBookingRefundOwner transactionBookingRefundOwner = new TransactionBookingRefundOwner();
        transactionBookingRefundOwner.setFromBookingId(fromBookingId);
        transactionBookingRefundOwner.setToMemberId(toMemberId);
        transactionBookingRefundOwner.setAmount(amount);
        transactionBookingRefundOwner.setResultCode(resultCode);
        transactionBookingRefundOwner.setDetail(detail);
        transactionBookingRefundOwner.setCreatedOn(createdOn);
        transactionBookingRefundOwner.setMemberBalance(memberBalance);

        transactionBookingRefundOwnerRepository.save(transactionBookingRefundOwner);
    }

    @Override
    public List<TransactionBookingRefundOwner> getTransactionBookingRefundOwnerByUserId(long id) {
        return transactionBookingRefundOwnerRepository.findAllByToMemberId(id);
    }
}
