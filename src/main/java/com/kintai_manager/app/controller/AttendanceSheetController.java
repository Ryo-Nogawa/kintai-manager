package com.kintai_manager.app.controller;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintai_manager.app.dto.DailyAttendanceRecord;
import com.kintai_manager.app.service.CreateMonthlyCalendarService;
import com.kintai_manager.app.service.validation.TargetMonthCheck;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/kintai_manager/attendance/sheet")
@RequiredArgsConstructor
public class AttendanceSheetController {

    private final CreateMonthlyCalendarService createMonthlyCalendarService;

    @GetMapping("show")
    public String attendanceSheetShow(@RequestParam String targetMonth, Model model) {
        try {
            TargetMonthCheck targetMonthCheck = new TargetMonthCheck();
            targetMonthCheck.checkTargetMonth(targetMonth);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("不正な対象月です。入力値： " + targetMonth);
        }
        List<DailyAttendanceRecord> DailyAttendanceRecordList = createMonthlyCalendarService.createMonthlyCalendar(
                "00000", targetMonth);
        model.addAttribute("dailyAttendanceRecordList", DailyAttendanceRecordList);
        return "attendance-sheet/show";
    }
}
