package com.example.holidayswap.controller.exchange;

import com.example.holidayswap.domain.entity.exchange.Exchange;
import com.example.holidayswap.service.exchange.IExchangeService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/exchange")
@AllArgsConstructor
public class ExchangeController {
    private final IExchangeService exchangeService;
    @PostMapping("/create")
    public void createExchange(@RequestBody Exchange exchange) {
        exchangeService.CreateExchange(exchange.getAvailableTimeIdOfUser1(), exchange.getTotalMemberOfUser1(), exchange.getAvailableTimeIdOfUser2(), exchange.getTotalMemberOfUser2(), exchange.getUserIdOfUser1(), exchange.getUserIdOfUser2(), exchange.getPriceOfUser1(), exchange.getPriceOfUser2(), exchange.getCheckInDateOfUser1(), exchange.getCheckOutDateOfUser1(), exchange.getCheckInDateOfUser2(), exchange.getCheckOutDateOfUser2());
    }
    @PutMapping("/update/{exchangeId}")
    public void updateExchange(@PathVariable Long exchangeId, @RequestBody Exchange exchange) {
        exchangeService.UpdateExchange(exchangeId, exchange.getAvailableTimeIdOfUser1(), exchange.getTotalMemberOfUser1(), exchange.getAvailableTimeIdOfUser2(), exchange.getTotalMemberOfUser2(), exchange.getPriceOfUser1(), exchange.getPriceOfUser2(), exchange.getCheckInDateOfUser1(), exchange.getCheckOutDateOfUser1(), exchange.getCheckInDateOfUser2(), exchange.getCheckOutDateOfUser2());
    }
    @PutMapping("/updateStatus/{exchangeId}")
    public void updateStatus(@PathVariable Long exchangeId, @RequestBody String status) throws IOException, InterruptedException, WriterException {
        exchangeService.UpdateStatus(exchangeId, status);
    }
}
