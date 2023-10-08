package com.example.holidayswap.controller.payment;

import com.example.holidayswap.domain.dto.response.payment.HistoryTransactionResponse;
import com.example.holidayswap.service.payment.IHistoryTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment/history")
@AllArgsConstructor
public class HistoryTransactionController {

    private final IHistoryTransactionService historyTransactionService;

    @GetMapping("/{id}")
    public ResponseEntity<List<HistoryTransactionResponse>> getHistoryTransactionByUserId(@PathVariable long id) throws ParseException {
        return ResponseEntity.ok(historyTransactionService.getHistoryTransactionByUserId(id));
    }
}
