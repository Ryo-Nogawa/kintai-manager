package com.kintai_manager.app.dto;

import lombok.Data;

@Data
public class TimeEventResult {
    // 勤怠種類
    private String eventType;
    // 打刻日
    private String day;
    // 打刻日時
    private String eventTime;
}
