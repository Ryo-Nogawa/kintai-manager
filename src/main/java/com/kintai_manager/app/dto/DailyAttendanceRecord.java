package com.kintai_manager.app.dto;

import lombok.Data;

@Data
public class DailyAttendanceRecord {
    // 日付
    private int date;

    // 曜日付き日付
    private String dayOfWeekWithDate;

    // 勤務開始時間
    private String workStartTime;

    // 勤務終了時間
    private String workEndTime;

    // 休憩開始時間
    private String breakStartTime;

    // 休憩終了時間
    private String breakEndTime;

    // 勤務開始時間（丸め処理後）
    private String roundedWorkStartTime;

    // 勤務終了時間（丸め処理後）
    private String roundedWorkEndTime;

    // 休憩開始時間（丸め処理後）
    private String roundedBreakStartTime;

    // 休憩終了時間（丸め処理後）
    private String roundedBreakEndTime;

    // 実働時間
    private String workingTime;
}
