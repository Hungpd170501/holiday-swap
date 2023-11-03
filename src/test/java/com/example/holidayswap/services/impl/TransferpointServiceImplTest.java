package com.example.holidayswap.services.impl;

import com.example.holidayswap.domain.entity.payment.Wallet;
import com.example.holidayswap.repository.booking.BookingDetailRepository;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.payment.AdminWalletRepository;
import com.example.holidayswap.repository.payment.AllLogRepository;
import com.example.holidayswap.repository.payment.TransactLogRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import com.example.holidayswap.service.BankException;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.payment.*;
import com.example.holidayswap.utils.RedissonLockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.redisson.RedissonReadLock;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferpointServiceImplTest {
    IWalletService walletService;
    IAllLogPayBookingService allLogPayBookingService;
    ILoggingService loggingService;
    TransactLogRepository transactLogRepository;
    BookingRepository bookingRepository;
    BookingDetailRepository bookingDetailRepository;
    WalletRepository walletRepository;
    UserService userService;
    AllLogRepository allLogRepository;
    AdminWalletRepository adminWalletRepository;
    ITransactionBookingRefundOwnerService transactionBookingRefundOwnerService;
    TransferPointServiceImpl tra;

    @BeforeEach
    void beforeEach(){
        walletService = mock(IWalletService.class);
        allLogPayBookingService = mock(IAllLogPayBookingService.class);
        loggingService = mock(ILoggingService.class);
        transactLogRepository = mock(TransactLogRepository.class);
        bookingRepository = mock(BookingRepository.class);
        bookingDetailRepository = mock(BookingDetailRepository.class);
        walletRepository = mock(WalletRepository.class);
        userService = mock(UserService.class);
        allLogRepository = mock(AllLogRepository.class);
        adminWalletRepository = mock(AdminWalletRepository.class);
        transactionBookingRefundOwnerService = mock(ITransactionBookingRefundOwnerService.class);
        tra = new TransferPointServiceImpl(walletService, allLogPayBookingService, loggingService, transactLogRepository, bookingRepository, bookingDetailRepository, walletRepository, userService, allLogRepository, adminWalletRepository, transactionBookingRefundOwnerService);
    }

    @Test
    void transferPoint_ShouldthrowError_WhenWalletNotFound() throws InterruptedException {
        RLock fairLock = mock(RLock.class);
        Object o = new Object();
        Wallet walletFrom = null;
        Wallet WalletTo = null;
        Long userIdFrom = 1L;
        Long userIdTo = 2L;
        when(RedissonLockUtils.getFairLock(anyString())).thenReturn((RLock) o);
        when(fairLock.tryLock(10, 10, TimeUnit.SECONDS)).thenReturn(true);

        when(walletService.GetWalletByUserId(1L)).thenReturn(walletFrom);
        when(walletService.GetWalletByUserId(2L)).thenReturn(WalletTo);
        when(fairLock.tryLock(10, 10, TimeUnit.SECONDS)).thenReturn(true);

        when(walletService.GetWalletByUserId(1L)).thenReturn(walletFrom);
        when(walletService.GetWalletByUserId(2L)).thenReturn(WalletTo);
        BankException actualException = assertThrows(BankException.class, () -> {
            tra.transferPoint(userIdFrom,userIdTo,100);
        });
        assertEquals("Account not found", actualException.getMessage());
    }
}
