package com.kintai_manager.app.dto;

import java.util.List;

import com.kintai_manager.app.entity.TimeEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlyCalendarResult {
    // 勤怠情報リスト
    private List<TimeEvent> timeEvents;

    // 月末日
    private int endDate;

    // 曜日リスト
    private List<String> dayOfWeekList;

}
