package com.example.holidayswap.services.impl;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.Wallet;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.payment.WalletRepository;
import com.example.holidayswap.service.BankException;
import com.example.holidayswap.service.payment.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WalletServiceImplTest {

    WalletRepository walletRepository;

    UserRepository userRepository;

    Wallet initialWallet;

    User initialUser;

    WalletServiceImpl walletService;

    @BeforeEach
    void beforeEach() {

        walletRepository = mock(WalletRepository.class);

        userRepository = mock(UserRepository.class);

        initialWallet = mock(Wallet.class);
        LocalDate d = LocalDate.of(1999, 1, 1);
        initialUser = User.builder().userId(1L).dob(d).wallet(null).build();
        walletService = new WalletServiceImpl(walletRepository, userRepository);
        initialWallet = Wallet.builder()
                .id(1L)
                .totalPoint(0D)
                .status(true)
                .build();
    }

    @Test
    void createWallet_ShouldThrowInvalidUserException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        BankException actualException = assertThrows(BankException.class, () -> {
            walletService.CreateWallet(1L);
        });
        assertEquals("User not found", actualException.getMessage());
    }

    @Test
    void createWallet_ShouldReturnWalletAlready_WhenWalletUserNotNull(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(initialUser));
        when(mock(User.class).getWallet()).thenReturn(initialWallet);
        initialUser.setWallet(initialWallet);
        Wallet actualWallet = walletService.CreateWallet(1L);
        assertEquals(initialWallet, actualWallet);
    }

    @Test
    void createWallet_ShouldReturnNewWallet_WhenUserWalletIsNull(){
        Wallet walet = mock(Wallet.class);
        User user = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(initialUser));
        when(user.getWallet()).thenReturn(null);
        when(walletRepository.save(ArgumentMatchers.any(Wallet.class))).thenReturn(initialWallet);
        initialWallet.setUser(initialUser);
        Wallet actualWallet = walletService.CreateWallet(1L);
        assertEquals(initialWallet.getUser(), actualWallet.getUser());
    }

    @Test
    void topUpWallet_ShouldReturnTrue_WhenTopUpSuccess(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(initialUser));
        when(walletRepository.findByUser(initialUser)).thenReturn(initialWallet);
        when(walletRepository.save(initialWallet)).thenReturn(initialWallet);
        boolean actual = walletService.TopUpWallet(1L, 100D);
        assertEquals(true, actual);
    }
}
