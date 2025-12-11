package com.kintai_manager.app.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.kintai_manager.app.dto.DailyAttendanceRecord;
import com.kintai_manager.app.dto.TimeEventResult;
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
        List<TimeEventResult> timeEvents = timeEventRepository.getTimeEvent(employeeId, targetMonth);
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
            dailyAttendanceRecord.setDate(i);
            dailyAttendanceRecord.setDayOfWeekWithDate(dayString + "(" + dayOfWeekJapanese + ")");

            for (TimeEventResult timeEvent : timeEvents) {
                // 日付が一致する勤怠データを取得
                if (timeEvent.getDay().equals(dayString)) {
                    // 勤怠データの種類によって、勤務開始時間、勤務終了時間、休憩開始時間、休憩終了時間を設定
                    if (timeEvent.getEventType().equals(EventType.WORK_START.name())) {
                        dailyAttendanceRecord.setWorkStartTime(timeEvent.getEventTime());
                        dailyAttendanceRecord.setRoundedWorkStartTime(timeEvent.getRoundedEventTime());
                    } else if (timeEvent.getEventType().equals(EventType.WORK_END.name())) {
                        dailyAttendanceRecord.setWorkEndTime(timeEvent.getEventTime());
                        dailyAttendanceRecord.setRoundedWorkEndTime(timeEvent.getRoundedEventTime());
                    } else if (timeEvent.getEventType().equals(EventType.BREAK_START.name())) {
                        dailyAttendanceRecord.setBreakStartTime(timeEvent.getEventTime());
                        dailyAttendanceRecord.setRoundedBreakStartTime(timeEvent.getRoundedEventTime());
                    } else if (timeEvent.getEventType().equals(EventType.BREAK_END.name())) {
                        dailyAttendanceRecord.setBreakEndTime(timeEvent.getEventTime());
                        dailyAttendanceRecord.setRoundedBreakEndTime(timeEvent.getRoundedEventTime());
                    }
                }
            }
            // 実働時間の算出
            if (dailyAttendanceRecord.getRoundedWorkStartTime() != null
                    && dailyAttendanceRecord.getRoundedWorkEndTime() != null) {
                // 丸め処理後の時間をLocalDateTimeに変換（フォーマット: uuuuMMddHHmm）
                DateTimeFormatter roundedTimeFormat = DateTimeFormatter.ofPattern("uuuuMMddHHmm");
                LocalDateTime workStartTime = LocalDateTime.parse(dailyAttendanceRecord.getRoundedWorkStartTime(),
                        roundedTimeFormat);
                LocalDateTime workEndTime = LocalDateTime.parse(dailyAttendanceRecord.getRoundedWorkEndTime(),
                        roundedTimeFormat);

                // 総勤務時間を計算（勤務終了時間 - 勤務開始時間）
                Duration workDuration = Duration.between(workStartTime, workEndTime);
                long totalMinutes = workDuration.toMinutes();

                // 休憩時間を計算（休憩開始時間と休憩終了時間が設定されている場合）
                if (dailyAttendanceRecord.getRoundedBreakStartTime() != null
                        && dailyAttendanceRecord.getRoundedBreakEndTime() != null) {
                    LocalDateTime breakStartTime = LocalDateTime.parse(
                            dailyAttendanceRecord.getRoundedBreakStartTime(), roundedTimeFormat);
                    LocalDateTime breakEndTime = LocalDateTime.parse(
                            dailyAttendanceRecord.getRoundedBreakEndTime(), roundedTimeFormat);
                    Duration breakDuration = Duration.between(breakStartTime, breakEndTime);
                    // 休憩時間を総勤務時間から引き算
                    totalMinutes -= breakDuration.toMinutes();
                }

                // 実働時間を時間と分に変換（0以下の場合は00:00に設定）
                String workingTime;
                if (totalMinutes <= 0) {
                    workingTime = "00:00";
                } else {
                    long hours = totalMinutes / 60;
                    long minutes = totalMinutes % 60;
                    workingTime = String.format("%02d:%02d", hours, minutes);
                }
                dailyAttendanceRecord.setWorkingTime(workingTime);
            }

            dailyAttendanceRecordList.add(dailyAttendanceRecord);
            System.out.println("dailyAttendanceRecord: " + dailyAttendanceRecord);
        }

        return dailyAttendanceRecordList;

    }
}