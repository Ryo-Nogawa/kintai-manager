package com.kintai_manager.app.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.kintai_manager.app.dto.DailyAttendanceRecord;
import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.enums.EventType;
import com.kintai_manager.app.repository.TimeEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMonthlyCalendarServiceImpl implements CreateMonthlyCalendarService {
    private final TimeEventRepository timeEventRepository;

    @Override
    public List<DailyAttendanceRecord> createMonthlyCalendar(String employeeId, String targetMonth) {
        // 対象年月の勤怠情報を取得
        List<TimeEvent> timeEvents = timeEventRepository.getTimeEvents(employeeId, targetMonth);
        // 対象年月の最終日を取得
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("uuuuMM");
        YearMonth yearMonth = YearMonth.parse(targetMonth, inputFormat);
        int endDate = yearMonth.atEndOfMonth().getDayOfMonth();
        // 日別勤怠情報リストを作成
        List<DailyAttendanceRecord> dailyAttendanceRecordList = new ArrayList<>();
        DateTimeFormatter yearMonthDayFormat = DateTimeFormatter.ofPattern("uuuuMMdd");
        for (int i = 1; i <= endDate; i++) {
            // 曜日付き日付の算出
            String dayString = String.format("%02d", i);
            String yearMonthDayString = targetMonth + dayString;
            LocalDate date = LocalDate.parse(yearMonthDayString, yearMonthDayFormat);
            String dayOfWeekJapanese = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
            DailyAttendanceRecord dailyAttendanceRecord = new DailyAttendanceRecord();
            dailyAttendanceRecord.setDayOfWeekWithDate(dayString + "(" + dayOfWeekJapanese + ")");

            for (TimeEvent timeEvent : timeEvents) {
                // 日付が一致する勤怠データを取得
                if (timeEvent.getDay().equals(dayString)) {
                    // 勤怠データの種類によって、勤務開始時間、勤務終了時間、休憩開始時間、休憩終了時間を設定
                    if (timeEvent.getEventType().equals(EventType.WORK_START.name())) {
                        dailyAttendanceRecord.setWorkStartTime(timeEvent.getEventTime());
                    } else if (timeEvent.getEventType().equals(EventType.WORK_END.name())) {
                        dailyAttendanceRecord.setWorkEndTime(timeEvent.getEventTime());
                    } else if (timeEvent.getEventType().equals(EventType.BREAK_START.name())) {
                        dailyAttendanceRecord.setBreakStartTime(timeEvent.getEventTime());
                    } else if (timeEvent.getEventType().equals(EventType.BREAK_END.name())) {
                        dailyAttendanceRecord.setBreakEndTime(timeEvent.getEventAt());
                    }
                }
            }
            dailyAttendanceRecordList.add(dailyAttendanceRecord);
        }

        return dailyAttendanceRecordList;

    }
}