package com.kintai_manager.app.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.kintai_manager.app.dto.MonthlyCalendarResult;

public class CreateMonthlyCalendarServiceImpl implements CreateMonthlyCalendarService {
    @Override
    public MonthlyCalendarResult createMonthlyCalendar(String targetMonth) {

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuuMM");
        YearMonth yearMonth = YearMonth.parse(targetMonth, inputFormat);

        MonthlyCalendarResult monthlyCalendarResult = new MonthlyCalendarResult();
        int endDate = yearMonth.atEndOfMonth().getDayOfMonth();
        monthlyCalendarResult.setEndDate(endDate);

        DateTimeFormatter yearMonthDayFormat = DateTimeFormatter.ofPattern("uuuuMMdd");
        List<String> dayOfWeekList = new ArrayList<>();
        for (int i = 1; i <= endDate; i++) {
            String endDateString = targetMonth + String.format("%02d", i);
            LocalDate date = LocalDate.parse(endDateString, yearMonthDayFormat);
            String dayOfWeekJapanese = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
            dayOfWeekList.add(dayOfWeekJapanese);
        }
        monthlyCalendarResult.setDayOfWeekList(dayOfWeekList);
        return monthlyCalendarResult;
    }
}