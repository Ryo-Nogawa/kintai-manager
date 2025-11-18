package com.kintai_manager.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    WORK_START("勤務開始"),
    WORK_END("勤務終了"),
    BREAK_START("休憩開始"),
    BREAK_END("休憩終了");

    private final String displayName;
}
