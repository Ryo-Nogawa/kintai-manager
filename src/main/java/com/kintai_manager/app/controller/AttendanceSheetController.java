package com.kintai_manager.app.controller;

import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kintai_manager.app.dto.MonthlyCalendarResult;
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
        MonthlyCalendarResult monthlyCalendarResult = createMonthlyCalendarService.createMonthlyCalendar("00000",
                targetMonth);
        model.addAttribute("timeEvents", monthlyCalendarResult.getTimeEvents());
        model.addAttribute("endDate", monthlyCalendarResult.getEndDate());
        model.addAttribute("dayOfWeekList", monthlyCalendarResult.getDayOfWeekList());
        return "attendance-sheet/show";
    }
}
