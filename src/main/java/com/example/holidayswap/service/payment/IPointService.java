package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.Point;

public interface IPointService {
    void CreateNewPointPrice(Double price);

    Point GetActivePoint();
}
