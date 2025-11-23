package com.kintai_manager.app.dto;

import lombok.Data;

@Data
public class DailyAttendanceRecord {
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
}
