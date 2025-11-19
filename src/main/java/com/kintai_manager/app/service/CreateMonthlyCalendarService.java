package com.kintai_manager.app.service;

import com.kintai_manager.app.dto.MonthlyCalendarResult;

public interface CreateMonthlyCalendarService {
    MonthlyCalendarResult createMonthlyCalendar(String targetMonth);
}
