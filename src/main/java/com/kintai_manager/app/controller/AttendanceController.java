package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai_manager.app.service.RegistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kintai-manager/attendance")
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
}
