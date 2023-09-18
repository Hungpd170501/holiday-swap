package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.entity.payment.MoneyTranfer;
import com.example.holidayswap.service.payment.IMoneyTranferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/money-tranfer")
public class MoneyTranferController {

    @Autowired
    private IMoneyTranferService moneyTranferService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getMoneyTranferTransactionByUserId(@PathVariable Long userId){
        List<MoneyTranfer> moneyTranfers = moneyTranferService.GetMoneyTranferTransactionByUserId(userId);

        return moneyTranfers.size()> 0 || !moneyTranfers.isEmpty() ? ResponseEntity.ok(moneyTranfers) : ResponseEntity.badRequest().body("No transaction found");
    }
}
