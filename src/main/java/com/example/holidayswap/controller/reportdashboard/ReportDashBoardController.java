package com.example.holidayswap.controller.reportdashboard;

import com.example.holidayswap.domain.dto.request.reportdashboard.ReportTotalBookingWeekRequest;
import com.example.holidayswap.domain.dto.response.reportdashboard.TotalBookingInWeek;
import com.example.holidayswap.service.reportdashboard.IReportDashBoardService;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;

@RestController()
@RequestMapping("/api/v1/reportdashboard")
@AllArgsConstructor
public class ReportDashBoardController {
    private final IReportDashBoardService reportDashBoardService;

    @GetMapping("/deposit/today")
    public Double pointDepositToDay() {
        return reportDashBoardService.pointDepositToDay();
    }
    @GetMapping("/deposit/thisweek")
    public Double pointDepositWeek() {
        return reportDashBoardService.pointDepositWeek();
    }
    @GetMapping("/deposit/thismonth")
    public Double pointDepositMonth() {
        return reportDashBoardService.pointDepositMonth();
    }
    @GetMapping("/deposit/date")
    public Double pointDepositDate(@RequestParam Date date){
        return reportDashBoardService.pointDepositDate(date);
    }
    @GetMapping("/deposit/monthandyear")
    public Double pointDepositeInMonthAndYear(@RequestParam Date date){
        return reportDashBoardService.pointDepositeInMonthAndYear(date);
    }
    @GetMapping("/commission/today")
    public Double commissionOfBookingToDay() {
        return reportDashBoardService.commissionOfBookingToDay();
    }
    @GetMapping("/commission/thisweek")
    public Double commissionOfBookingWeek() {
        return reportDashBoardService.commissionOfBookingWeek();
    }
    @GetMapping("/commission/thismonth")
    public Double commissionOfBookingMonth() {
        return reportDashBoardService.commissionOfBookingMonth();
    }
    @GetMapping("/commission/date")
    public Double commissionOfBookingDate(@RequestParam Date date){
        return reportDashBoardService.commissionOfBookingDate(date);
    }
    @GetMapping("/commission/monthandyear")
    public Double commissionOfBookingInMonthAndYear(@RequestParam Date date){
        return reportDashBoardService.commissionOfBookingInMonthAndYear(date);
    }

    @PostMapping("/totalbooking/week")
    public ResponseEntity<?> totalBookingInWeek(@RequestBody ReportTotalBookingWeekRequest reportTotalBookingWeekRequest){
        return ResponseEntity.ok(reportDashBoardService.totalBookingInWeek(reportTotalBookingWeekRequest.getMonday(),reportTotalBookingWeekRequest.getType()));
    }
}
