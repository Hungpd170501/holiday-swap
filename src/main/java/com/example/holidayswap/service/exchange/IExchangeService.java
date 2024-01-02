package com.example.holidayswap.service.exchange;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Date;

public interface IExchangeService {
    void CreateExchange(Long availableTimeIdOfUser1, int totalMemberOfUser1, Long availableTimeIdOfUser2, int totalMemberOfUser2, Long userIdOfUser1, Long userIdOfUser2, Double priceOfUser1, Double priceOfUser2, Date startDate1, Date endDate1, Date startDate2, Date endDate2);
    void UpdateStatus(Long exchangeId, String status) throws IOException, InterruptedException, WriterException;
    void UpdateExchange(Long exchangeId, Long availableTimeIdOfUser1, int totalMemberOfUser1, Long availableTimeIdOfUser2, int totalMemberOfUser2, Double priceOfUser1, Double priceOfUser2, Date startDate1, Date endDate1, Date startDate2, Date endDate2);

}
