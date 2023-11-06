package com.example.holidayswap.services.impl;

import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.payment.AdminWalletRepository;
import com.example.holidayswap.repository.payment.AllLogRepository;
import com.example.holidayswap.repository.payment.TransactLogRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.payment.*;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class TransferpointServiceImplTest {
    IWalletService walletService;
    IAllLogPayBookingService allLogPayBookingService;
    ILoggingService loggingService;
    TransactLogRepository transactLogRepository;
    BookingRepository bookingRepository;
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
        walletRepository = mock(WalletRepository.class);
        userService = mock(UserService.class);
        allLogRepository = mock(AllLogRepository.class);
        adminWalletRepository = mock(AdminWalletRepository.class);
        transactionBookingRefundOwnerService = mock(ITransactionBookingRefundOwnerService.class);
        tra = new TransferPointServiceImpl(walletService, allLogPayBookingService, loggingService, transactLogRepository, walletRepository, userService, allLogRepository, adminWalletRepository, transactionBookingRefundOwnerService);
    }

//    @Test
//    void transferPoint_ShouldthrowError_WhenWalletNotFound() throws InterruptedException {
//        RLock fairLock = mock(RLock.class);
//        Wallet walletFrom = new Wallet();
//        walletFrom.setId(1L);
//        Long userIdFrom = 1L;
//        Long userIdTo = 2L;
//        try(MockedStatic<RedissonLockUtils> redissonLockUtilsMockedStatic = Mockito.mockStatic(RedissonLockUtils.class)) {
//            redissonLockUtilsMockedStatic.when(() -> RedissonLockUtils.getFairLock("wallet-" + walletFrom.getId())).thenReturn(fairLock);
//            when(fairLock.tryLock(10, 10, TimeUnit.SECONDS)).thenReturn(true);
//
//            when(walletService.GetWalletByUserId(1L)).thenReturn(walletFrom);
//            when(walletService.GetWalletByUserId(2L)).thenReturn(null);
//            BankException actualException = assertThrows(BankException.class, () -> {
//                tra.transferPoint(userIdFrom, userIdTo, 100);
//            });
//            assertEquals("Account not found", actualException.getMessage());
//        }
//
//        }

}
