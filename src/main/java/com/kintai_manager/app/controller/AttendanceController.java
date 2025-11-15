package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.service.RegistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kintai_manager/attendance")
public class AttendanceController {

    private final RegistService registService;

    // 勤務開始リクエスト
    @PostMapping("/work/start")
    public String startWork(Model model) {
        // 開始時間を登録
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setEmployeeId("00000");
        timeEvent.setEventType("WORK_START");
        registService.registAttendance(timeEvent);

        model.addAttribute("attendance", "勤務開始");
        return "attendance-done";
    }

    // 勤務終了リクエスト
    @PostMapping("/work/end")
    public String endWork(Model model) {
        // 終了時間を登録
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setEmployeeId("00000");
        timeEvent.setEventType("WORK_END");
        registService.registAttendance(timeEvent);

        model.addAttribute("attendance", "勤務終了");
        return "attendance-done";
    }

    // 休憩開始リクエスト
    @PostMapping("/break/start")
    public String startBreak(Model model) {
        // 休憩開始時間を登録
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setEmployeeId("00000");
        timeEvent.setEventType("BREAK_START");
        registService.registAttendance(timeEvent);

        model.addAttribute("attendance", "休憩開始");
        return "attendance-done";
    }

    // 休憩終了リクエスト
    @PostMapping("/break/end")
    public String endBreak(Model model) {
        // 休憩終了時間を登録
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setEmployeeId("00000");
        timeEvent.setEventType("BREAK_END");
        registService.registAttendance(timeEvent);

        model.addAttribute("attendance", "休憩終了");
        return "attendance-done";
    }
}
