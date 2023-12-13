package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.HistoryTransactionResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.service.auth.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HistoryTransactionServiceImpl implements IHistoryTransactionService{

    private final ITransferPointService transferPointService;

    private final IMoneyTranferService moneyTranferService;

    private final IAllLogPayBookingService allLogPayBookingService;

    private final ITransactionBookingRefundOwnerService transactionBookingRefundOwnerService;





    @Override
    public List<HistoryTransactionResponse> getHistoryTransactionByUserId(long id) throws ParseException {

        var listHistoryTransaction = new ArrayList<HistoryTransactionResponse>();

        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;



        // List Transferpoint
        var listTransferPoint = transferPointService.getTransactionTranferPointByUserId(id);
        var listDepositePoint = moneyTranferService.GetMoneyTranferTransactionByUserId(id);
        var listLogPayBooking = allLogPayBookingService.getAllLogPayBookingByUserId(id);
        var listLogRefundBookingOwner = transactionBookingRefundOwnerService.getTransactionBookingRefundOwnerByUserId(id);

        if(listTransferPoint != null){
            for(var transferPoint : listTransferPoint){
                var historyTransaction = new HistoryTransactionResponse();
                historyTransaction.setFrom(transferPoint.getFrom());
                historyTransaction.setTo(transferPoint.getTo());
                historyTransaction.setDateConvert(transferPoint.getDate());
                historyTransaction.setAmount(transferPoint.getAmount());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = dateFormat.parse(transferPoint.getDate());
                historyTransaction.setCreatedOn(date);
                historyTransaction.setMessage(transferPoint.getFrom() + " transfer to " + transferPoint.getTo() + " " + transferPoint.getAmount() + " point");
                historyTransaction.setTotalPoint(transferPoint.getTotalPoint());
                if(transferPoint.getStatusPointTransfer() == EnumPaymentStatus.StatusPointTransfer.POINT_RECEIVE){
                    historyTransaction.setType(EnumPaymentStatus.TransactionStatus.RECIVED);
                    if(transferPoint.getDetail().equals("Refund")){
                        historyTransaction.setAmount(transferPoint.getAmount() + transferPoint.getCommission());
                    }
                }else {
                    historyTransaction.setType(EnumPaymentStatus.TransactionStatus.SEND);
                }
                if(transferPoint.getStatus().equals("SUCCESS")){
                    historyTransaction.setStatus(EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
                }else {
                    historyTransaction.setStatus(EnumPaymentStatus.StatusMoneyTranfer.FAILED);
                }
                if(transferPoint.getStatusPointTransfer() == EnumPaymentStatus.StatusPointTransfer.POINT_RECEIVE && !transferPoint.getStatus().equals("SUCCESS")){}
                else {
                    listHistoryTransaction.add(historyTransaction);
                }

            }
        }
        if(listDepositePoint != null){
            for(var depositePoint : listDepositePoint){
                var historyTransaction = new HistoryTransactionResponse();
                historyTransaction.setFrom("VN_PAY");
                historyTransaction.setTo(depositePoint.getUser().getUsername());
                historyTransaction.setAmount("+ " + String.valueOf(depositePoint.getAmount()));
                historyTransaction.setDateConvert(depositePoint.getPaymentDate());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = dateFormat.parse(depositePoint.getPaymentDate());
                historyTransaction.setCreatedOn(date);
                historyTransaction.setMessage("Admin transfer to " + depositePoint.getUser().getUsername() + " " + depositePoint.getAmount() + " point");
                historyTransaction.setTotalPoint(depositePoint.getTotalPoint());
                historyTransaction.setType(EnumPaymentStatus.TransactionStatus.RECIVED);
                historyTransaction.setStatus(depositePoint.getStatus());
                listHistoryTransaction.add(historyTransaction);
            }
        }
        if(listLogPayBooking !=null){
            for(var logPayBooking : listLogPayBooking){
                var historyTransaction = new HistoryTransactionResponse();
                historyTransaction.setFrom(user.getUsername());
                historyTransaction.setTo(String.valueOf(logPayBooking.getToBookingId()));
                historyTransaction.setDateConvert(logPayBooking.getCreatedOn());
                historyTransaction.setAmount("- "  + logPayBooking.getAmount());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = dateFormat.parse(logPayBooking.getCreatedOn());
                historyTransaction.setCreatedOn(date);
                historyTransaction.setMessage(historyTransaction.getFrom() + " pay for booking" + logPayBooking.getToBookingId());
                historyTransaction.setTotalPoint(logPayBooking.getFromBalance());
                historyTransaction.setType(EnumPaymentStatus.TransactionStatus.SEND);
                if(logPayBooking.getResultCode() == EnumPaymentStatus.BankCodeError.SUCCESS) {
                    historyTransaction.setStatus(EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
                }else {
                    historyTransaction.setStatus(EnumPaymentStatus.StatusMoneyTranfer.FAILED);
                }
                listHistoryTransaction.add(historyTransaction);
            }
        }
        if(listLogRefundBookingOwner != null){
            for(var logRefundBookingOwner : listLogRefundBookingOwner){
                var historyTransaction = new HistoryTransactionResponse();
                historyTransaction.setFrom(String.valueOf(logRefundBookingOwner.getFromBookingId()));
                historyTransaction.setDateConvert(logRefundBookingOwner.getCreatedOn());
                historyTransaction.setTo(user.getUsername());
                historyTransaction.setAmount("+ " + logRefundBookingOwner.getAmount());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = dateFormat.parse(logRefundBookingOwner.getCreatedOn());
                historyTransaction.setCreatedOn(date);
                historyTransaction.setMessage(historyTransaction.getFrom() + " refund for booking" + logRefundBookingOwner.getFromBookingId());
                historyTransaction.setTotalPoint(logRefundBookingOwner.getMemberBalance());
                historyTransaction.setType(EnumPaymentStatus.TransactionStatus.RECIVED);
                historyTransaction.setStatus(EnumPaymentStatus.StatusMoneyTranfer.SUCCESS);
                listHistoryTransaction.add(historyTransaction);
            }
        }

        return listHistoryTransaction.stream().sorted(Comparator.comparing(HistoryTransactionResponse::getCreatedOn).reversed()).collect(Collectors.toList());
    }
}
