package com.example.holidayswap.controller.exchange;

import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeUpdatingRequest;
import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeWithDetailResponse;
import com.example.holidayswap.service.exchange.IExchangeService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/exchange")
@AllArgsConstructor
public class ExchangeController {
    private final IExchangeService exchangeService;

    @GetMapping("/current-user")
    public Page<ExchangeWithDetailResponse> getExchangesByUserId(
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "exchangeId") String sortProps,
            @RequestParam(defaultValue = "desc") String sortDirection
    ){
        return exchangeService.getExchangesByUserId(limit, offset, sortProps, sortDirection);
    }

    @GetMapping("/{exchangeId}")
    public ResponseEntity<ExchangeResponse> getExchangeById(@PathVariable("exchangeId") Long exchangeId) {
        return ResponseEntity.ok(exchangeService.getExchangeById(exchangeId));
    }

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
    public ResponseEntity<Void> approveExchange(@PathVariable Long exchangeId) throws InterruptedException, MessagingException, IOException, WriterException, IOException {
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
