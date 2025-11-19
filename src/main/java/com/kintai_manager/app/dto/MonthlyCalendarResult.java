package com.kintai_manager.app.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlyCalendarResult {
    // 月末日
    private int endDate;

    // 曜日リスト
    private List<String> dayOfWeekList;

}
