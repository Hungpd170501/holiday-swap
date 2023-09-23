package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.payment.AdminWallet;
import com.example.holidayswap.domain.entity.payment.Point;
import com.example.holidayswap.domain.entity.payment.Wallet;
import com.example.holidayswap.service.payment.IPointService;
import com.example.holidayswap.service.payment.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/point")
public class PointController {

    @Autowired
    private IPointService pointService;
    @Autowired
    private IWalletService walletService;

    @PostMapping("/create")
    public void CreateNewPointPrice(Double price){
        pointService.CreateNewPointPrice(price);
    }
    @GetMapping
    public ResponseEntity<Point> GetActivePoint(){
        return ResponseEntity.ok(pointService.GetActivePoint());
    }
    @GetMapping("/admin")
    public ResponseEntity<?> GetPointAdminWallet(){
        return ResponseEntity.ok(pointService.GetPointAdminWallet());
    }
    @GetMapping("/user_wallet")
    public ResponseEntity<?> GetPointUserWallet(){
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        Wallet wallet = walletService.GetWalletByUserId(user.getUserId());
        return wallet == null ? ResponseEntity.badRequest().body("Wallet not Found") : ResponseEntity.ok(wallet);
    }
}
