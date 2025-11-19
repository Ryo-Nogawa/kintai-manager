package com.kintai_manager.app.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import com.kintai_manager.app.dto.MonthlyCalendarResult;

public class CreateMonthlyCalendarServiceImpl implements CreateMonthlyCalendarService {
    @Override
    public MonthlyCalendarResult createMonthlyCalendar(String targetMonth) {

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuuMM");
        YearMonth yearMonth = YearMonth.parse(targetMonth, inputFormat);

        MonthlyCalendarResult monthlyCalendarResult = new MonthlyCalendarResult();
        monthlyCalendarResult.setEndDate(yearMonth.atEndOfMonth().getDayOfMonth());
        return monthlyCalendarResult;
    }
}