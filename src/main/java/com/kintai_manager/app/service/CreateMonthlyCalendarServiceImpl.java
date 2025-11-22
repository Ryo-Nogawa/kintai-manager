package com.kintai_manager.app.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.kintai_manager.app.dto.MonthlyCalendarResult;
import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.repository.TimeEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMonthlyCalendarServiceImpl implements CreateMonthlyCalendarService {
    private final TimeEventRepository timeEventRepository;

    @Override
    public MonthlyCalendarResult createMonthlyCalendar(String employeeId, String targetMonth) {

        // 対象年月の勤怠情報を取得
        List<TimeEvent> timeEvents = timeEventRepository.getTimeEvents(employeeId, targetMonth);
        MonthlyCalendarResult monthlyCalendarResult = new MonthlyCalendarResult();
        monthlyCalendarResult.setTimeEvents(timeEvents);

        // 対象年月の最終日を取得
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuuMM");
        YearMonth yearMonth = YearMonth.parse(targetMonth, inputFormat);

        int endDate = yearMonth.atEndOfMonth().getDayOfMonth();
        monthlyCalendarResult.setEndDate(endDate);

        // 対象年月の日付と曜日を紐づけて、曜日リストを作成
        DateTimeFormatter yearMonthDayFormat = DateTimeFormatter.ofPattern("uuuuMMdd");
        List<String> dayOfWeekList = new ArrayList<>();
        for (int i = 1; i <= endDate; i++) {
            String dayString = String.format("%02d", i);
            String yearMonthDayString = targetMonth + dayString;
            LocalDate date = LocalDate.parse(yearMonthDayString, yearMonthDayFormat);
            String dayOfWeekJapanese = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
            dayOfWeekList.add(dayString + "(" + dayOfWeekJapanese + ")");
        }
        monthlyCalendarResult.setDayOfWeekList(dayOfWeekList);
        return monthlyCalendarResult;
    }
}