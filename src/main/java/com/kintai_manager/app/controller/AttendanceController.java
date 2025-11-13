package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai_manager.app.service.RegistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kintai_manager/attendance")
public class AttendanceController {

    private final RegistService registService;

    // 勤務開始リクエスト
    @PostMapping("/start")
    public String startAttendance(Model model) {
        // 開始時間を登録
        registService.registAttendance("12345", "WORK_START");
        model.addAttribute("attendance", "勤務開始");
        return "attendance-done";
    }

    // 勤務終了リクエスト
    @PostMapping("/end")
    public String endAttendance(Model model) {
        // 終了時間を登録
        registService.registAttendance("12345", "WORK_END");
        model.addAttribute("attendance", "勤務終了");
        return "attendance-done";
    }

    // 休憩開始リクエスト
    @PostMapping("/break/start")
    public String startBreak(Model model) {
        // 休憩開始時間を登録
        registService.registAttendance("12345", "BREAK_START");
        model.addAttribute("attendance", "休憩開始");
        return "attendance-done";
    }
}
