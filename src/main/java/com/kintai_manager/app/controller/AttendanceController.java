package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kintai-manager/attendance")
public class AttendanceController {

    @PostMapping("/start")
    public String startAttendance(Model model) {
        model.addAttribute("attendance", "勤務開始");
        return "attendance-done";
    }
}
