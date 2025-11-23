package com.kintai_manager.app.service;

import java.util.List;

import com.kintai_manager.app.dto.DailyAttendanceRecord;

public interface CreateMonthlyCalendarService {
    List<DailyAttendanceRecord> createMonthlyCalendar(String employeeId, String targetMonth);
}
