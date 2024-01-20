package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.payment.*;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.payment.AdminWalletRepository;
import com.example.holidayswap.repository.payment.AllLogRepository;
import com.example.holidayswap.repository.payment.TransactLogRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import com.example.holidayswap.service.BankException;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.utils.Helper;
import com.example.holidayswap.utils.RedissonLockUtils;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferPointServiceImpl implements ITransferPointService {

    @Autowired
    private IWalletService walletService;

    @Autowired
    private IAllLogPayBookingService allLogPayBookingService;

    @Autowired
    private ILoggingService loggingService;

    @Autowired
    private TransactLogRepository transactLogRepository;

    @Autowired
    private ITransactionBookingRefundOwnerService transactionBookingRefundOwnerService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AllLogRepository allLogRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private final PushNotificationService pushNotificationService;
    private final AdminWalletRepository adminWalletRepository;


    @Override
    @Transactional(rollbackFor = {BankException.class, InterruptedException.class})
    public TransferResponse transferPoint(long from, long to, Double amount) throws InterruptedException {
        Wallet fromWallet;
        Wallet toWallet;
        String currentDate = null;

        RLock fairLock = RedissonLockUtils.getFairLock("wallet-" + from);
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                fromWallet = walletService.GetWalletByUserId(from);
                toWallet = walletService.GetWalletByUserId(to);
                if (fromWallet == null || toWallet == null) {
                    throw new BankException("Account not found");
                }
                if (fromWallet.getTotalPoint() < amount) {
                    String detail = "Account " + fromWallet.getUser().getUsername() + " does not have enough balance";
                    loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, detail, fromWallet.getTotalPoint(), toWallet.getTotalPoint(), 0D);
                    throw new BankException(detail);
                }

                boolean check = fromWallet.withdraw(amount);
                if (!check) {
                    String detail = "Account " + fromWallet.getUser().getUsername() + " does not have enough balance";
                    loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, detail, fromWallet.getTotalPoint(), toWallet.getTotalPoint(), 0D);
                    throw new BankException(detail);
                }

                toWallet.setTotalPoint((toWallet.getTotalPoint() + amount));
                currentDate = Helper.getCurrentDate();
                TransactLog transactLog = new TransactLog();
                transactLog.setAmountPoint(amount);
                transactLog.setCreatedOn(currentDate);
                transactLog.setWalletFrom(fromWallet);
                transactLog.setWalletTo(toWallet);
                transactLog.setToTotalPoint(toWallet.getTotalPoint());
                transactLog.setFromTotalPoint(fromWallet.getTotalPoint());
                walletRepository.save(fromWallet);
                walletRepository.save(toWallet);
                transactLogRepository.save(transactLog);
                var notificationRequestForUserBooking = new NotificationRequest();
                notificationRequestForUserBooking.setSubject("Transfer point");
                notificationRequestForUserBooking.setContent("transfer from" + fromWallet.getUser().getUsername() + amount + "point");
                notificationRequestForUserBooking.setToUserId(to);
                pushNotificationService.createNotification(notificationRequestForUserBooking);
                notificationRequestForUserBooking.setSubject("Transfer success");
                notificationRequestForUserBooking.setContent("transfer to" + toWallet.getUser().getUsername() + amount + "point");
                notificationRequestForUserBooking.setToUserId(from);
                pushNotificationService.createNotification(notificationRequestForUserBooking);


                loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.SUCCESS, "Success", fromWallet.getTotalPoint(), toWallet.getTotalPoint(), 0D);
            } finally {
                fairLock.unlock();
            }
        }
        return new TransferResponse(EnumPaymentStatus.BankCodeError.SUCCESS, "Success", currentDate);
    }

    @Override
    public List<TransactionTranferPointResponse> getTransactionTranferPointByUserId(Long userId) {
        List<AllLog> allLogs = allLogRepository.findAllByFromIdOrToId(userId, userId);
        return convertAllLogToTransactionTranferPointResponse(allLogs, userId);
    }

    @Override
    public List<TransactionTranferPointResponse> convertAllLogToTransactionTranferPointResponse(List<AllLog> allLogs, Long userId) {
        return allLogs.stream()
                .filter(allLog -> !userService.getUserName(allLog.getFromId(), allLog.getFromId()).equals("booking"))
                .map(allLog -> {
            TransactionTranferPointResponse transactionTranferPointResponse = new TransactionTranferPointResponse();
            transactionTranferPointResponse.setId(allLog.getId());
            transactionTranferPointResponse.setFrom(userService.getUserName(allLog.getFromId(),allLog.getFromId()));
            transactionTranferPointResponse.setTo(userService.getUserById(allLog.getToId()).getUsername());
            transactionTranferPointResponse.setDetail(allLog.getDetail());
            if (allLog.getFromId() == userId) {
                transactionTranferPointResponse.setTotalPoint(allLog.getFromBalance());
                transactionTranferPointResponse.setAmount("-" + allLog.getAmount());
                transactionTranferPointResponse.setStatusPointTransfer(EnumPaymentStatus.StatusPointTransfer.POINT_TRANSFER);
                transactionTranferPointResponse.setCommission(0D);
            } else {
                transactionTranferPointResponse.setTotalPoint(allLog.getToBalance());
                Double amount = allLog.getAmount() - allLog.getCommission();
                transactionTranferPointResponse.setAmount("+" + amount);
                transactionTranferPointResponse.setStatusPointTransfer(EnumPaymentStatus.StatusPointTransfer.POINT_RECEIVE);
                transactionTranferPointResponse.setCommission(allLog.getCommission());
            }

            // khi xong giao dich thi ghi lai so du cua vi cua 2 ben
            transactionTranferPointResponse.setStatus(allLog.getResultCode().name());
            transactionTranferPointResponse.setDate(allLog.getCreatedOn());
            return transactionTranferPointResponse;

        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = {BankException.class, InterruptedException.class})
    public TransferResponse payBooking(Booking booking) throws InterruptedException {
        Wallet fromWallet;

        RLock fairLock = RedissonLockUtils.getFairLock("wallet-" + booking.getUserBookingId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);

        if (tryLock) {
            try {
                fromWallet = walletService.GetWalletByUserId(booking.getUserBookingId());
                if (fromWallet.getTotalPoint() < booking.getPrice()) {
                    String detail = "Account " + fromWallet.getUser().getUsername() + " does not have enough balance";
                    allLogPayBookingService.saveLog(booking.getUserBookingId(), booking.getId(), booking.getPrice(), EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, detail, Helper.getCurrentDate(), fromWallet.getTotalPoint());
                    throw new BankException(detail);
                }

                fromWallet.withdraw(booking.getPrice());
                //TODO get list Bookingdetail by booking id

                allLogPayBookingService.saveLog(booking.getUserBookingId(), booking.getId(), booking.getPrice(), EnumPaymentStatus.BankCodeError.SUCCESS, "booking from " + booking.getCheckInDate() + " to " + booking.getCheckOutDate(), Helper.getCurrentDate(), fromWallet.getTotalPoint());

                walletRepository.save(fromWallet);
            } finally {
                fairLock.unlock();
            }
        }

        return new TransferResponse(EnumPaymentStatus.BankCodeError.SUCCESS, "Success", Helper.getCurrentDate());
    }

    @Override
    public TransferResponse returnPoint(long from, long to, Double amount, Double commision) throws InterruptedException {
        Wallet fromWallet;
        AdminWallet adminWallet;
        Wallet toWallet;
        Double total;
        adminWallet = adminWalletRepository.findFirstByOrderByIdDesc();
        RLock fairLock = RedissonLockUtils.getFairLock("wallet-" + from);
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                fromWallet = walletService.GetWalletByUserId(from);
                toWallet = walletService.GetWalletByUserId(to);
                if (fromWallet.getTotalPoint() < amount) {
                    String detail = "Account " + fromWallet.getUser().getUsername() + " does not have enough balance";
                    loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.BALANCE_NOT_ENOUGH, detail, fromWallet.getTotalPoint(), toWallet.getTotalPoint(), 0D);
                    throw new BankException(detail);
                }

                fromWallet.withdraw(amount);
                total = amount + commision;
                adminWallet.setTotalPoint(adminWallet.getTotalPoint() - commision);
                toWallet.setTotalPoint(toWallet.getTotalPoint() + total);

                walletRepository.save(toWallet);
                loggingService.saveLog(from, to, amount, EnumPaymentStatus.BankCodeError.SUCCESS, "Refund", fromWallet.getTotalPoint(), toWallet.getTotalPoint(), commision);
                walletRepository.save(fromWallet);

                var notificationRequestForUserBooking = new NotificationRequest();
                notificationRequestForUserBooking.setSubject("Refund point cancelled booking" + total + "point");
                notificationRequestForUserBooking.setContent(fromWallet.getUser().getUsername() + "refund point for you" + total + "point");
                notificationRequestForUserBooking.setToUserId(to);
                pushNotificationService.createNotification(notificationRequestForUserBooking);
            } finally {
                fairLock.unlock();
            }
        }
        return new TransferResponse(EnumPaymentStatus.BankCodeError.SUCCESS, "Success", Helper.getCurrentDate());
    }

    @Override
    @Transactional
    public void refundPointBookingToOwner(Long bookingId) throws InterruptedException {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new BankException("Booking not found");
        }
        Wallet ownerWallet;
        AdminWallet adminWallet;
        adminWallet = adminWalletRepository.findFirstByOrderByIdDesc();
        if (adminWallet == null) {
            adminWallet = new AdminWallet();
            adminWallet.setTotalPoint(0D);
            adminWalletRepository.save(adminWallet);
        }
        RLock fairLock = RedissonLockUtils.getFairLock("wallet-" + booking.getOwnerId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                ownerWallet = walletService.GetWalletByUserId(booking.getOwnerId());
                if (ownerWallet == null) {
                    throw new BankException("Account not found");
                }


                ownerWallet.setTotalPoint(ownerWallet.getTotalPoint() + booking.getActualPrice());
                adminWallet.setTotalPoint(adminWallet.getTotalPoint() + booking.getPrice() * booking.getCommission());
                transactionBookingRefundOwnerService.saveLog(booking.getId(), booking.getOwnerId(), booking.getActualPrice(), EnumPaymentStatus.BankCodeError.SUCCESS, "booking " + booking.getCheckInDate() + " to " + booking.getCheckOutDate(), Helper.getCurrentDate(), ownerWallet.getTotalPoint());
                walletRepository.save(ownerWallet);
                var notificationRequestForUserBooking = new NotificationRequest();
                notificationRequestForUserBooking.setSubject("Refund point booking" + booking.getActualPrice() + "point");
                notificationRequestForUserBooking.setContent(booking.getId() + "refund point for you" + booking.getActualPrice() + "point");
                notificationRequestForUserBooking.setToUserId(booking.getOwnerId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);
                loggingService.saveLog(bookingId, booking.getOwnerId(), booking.getActualPrice(), EnumPaymentStatus.BankCodeError.SUCCESS, "Success", 0D, ownerWallet.getTotalPoint(), booking.getCommission());
                booking.setTransferStatus(EnumBookingStatus.TransferStatus.SUCCESS);
                bookingRepository.save(booking);
            } finally {
                fairLock.unlock();
            }
        }


    }

    @Override
    public void refundPointBookingToUser(Long bookingId) throws InterruptedException {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new BankException("Booking not found");
        }
        Wallet userWallet;
        RLock fairLock = RedissonLockUtils.getFairLock("wallet-" + booking.getOwnerId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                userWallet = walletService.GetWalletByUserId(booking.getUserBookingId());
                if (userWallet == null) {
                    throw new BankException("Account not found");
                }
                userWallet.setTotalPoint(userWallet.getTotalPoint() + booking.getPrice());
                transactionBookingRefundOwnerService.saveLog(booking.getId(), booking.getUserBookingId(), booking.getPrice(), EnumPaymentStatus.BankCodeError.SUCCESS, "booking " + booking.getCheckInDate() + " to " + booking.getCheckOutDate(), Helper.getCurrentDate(), userWallet.getTotalPoint());
                walletRepository.save(userWallet);
                var notificationRequestForUserBooking = new NotificationRequest();
                notificationRequestForUserBooking.setSubject("Refund point booking" + booking.getPrice() + "point");
                notificationRequestForUserBooking.setContent(booking.getId() + "refund point for you" + booking.getPrice() + "point");
                notificationRequestForUserBooking.setToUserId(booking.getUserBookingId());
                pushNotificationService.createNotification(notificationRequestForUserBooking);
//                loggingService.saveLog(bookingId, booking.getUserBookingId(), booking.getActualPrice(), EnumPaymentStatus.BankCodeError.SUCCESS, "Success", 0D, ownerWallet.getTotalPoint(), 0D);
                booking.setTransferStatus(EnumBookingStatus.TransferStatus.REFUND_USER);
                booking.setStatus(EnumBookingStatus.BookingStatus.CANCELLED);
                bookingRepository.save(booking);
            } finally {
                fairLock.unlock();
            }
        }
    }
}