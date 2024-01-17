package com.example.holidayswap.controller.exchange;

import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeUpdatingRequest;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.service.exchange.IExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchange")
@AllArgsConstructor
public class ExchangeController {
    private final IExchangeService exchangeService;

    @PostMapping("/create")
    public ResponseEntity<ExchangeResponse> createExchange(@RequestBody ExchangeCreatingRequest exchangeCreatingRequest) {
        return ResponseEntity.ok(exchangeService.create(exchangeCreatingRequest));
    }

    @PatchMapping("/{exchangeId}")
    public ResponseEntity<Void> updateExchange(@PathVariable Long exchangeId, @RequestBody ExchangeUpdatingRequest exchangeUpdatingRequest) {
        exchangeService.updateBaseData(exchangeId, exchangeUpdatingRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{exchangeId}/next-step")
    public ResponseEntity<Void> approveExchange(@PathVariable Long exchangeId) throws InterruptedException {
        exchangeService.updateNextStatus(exchangeId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{exchangeId}/previous-step")
    public ResponseEntity<Void> updateToPreviousStatus(@PathVariable Long exchangeId) throws InterruptedException {
        exchangeService.updatePreviousStatus(exchangeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{exchangeId}/cancel")
    public ResponseEntity<Void> cancelExchange(@PathVariable Long exchangeId) throws InterruptedException {
        exchangeService.cancel(exchangeId);
        return ResponseEntity.noContent().build();
    }

}
