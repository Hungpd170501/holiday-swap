package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.domain.entity.payment.Point;
import com.example.holidayswap.repository.payment.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Service
public class PointServiceImpl implements IPointService{

    @Autowired
    private PointRepository pointRepository;

    @Override
    public void CreateNewPointPrice(Double price) {
        // TODO Auto-generated method stub

        Point oldPoint = pointRepository.findByPointStatus(EnumPaymentStatus.StatusPoint.ACTIVE);
        if(oldPoint != null){
            oldPoint.setPointStatus(EnumPaymentStatus.StatusPoint.INACTIVE);
            pointRepository.save(oldPoint);
        }
        Point point = new Point();
        point.setPointPrice(price);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = formatter.format(cld.getTime());
        point.setPointCreatedDate(createDate);
        point.setPointStatus(EnumPaymentStatus.StatusPoint.ACTIVE);
        pointRepository.save(point);
    }

    @Override
    public Point GetActivePoint(){
        return pointRepository.findByPointStatus(EnumPaymentStatus.StatusPoint.ACTIVE);
    }
}
