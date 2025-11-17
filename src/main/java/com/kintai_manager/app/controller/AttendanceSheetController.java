package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai_manager.app.entity.TimeEvent;

@Controller
@RequestMapping("/kintai_manager/attendance/sheet/")
public class AttendanceSheetController {

    @GetMapping("index")
    public String index() {
        // 勤務シートのトップページを表示
        TimeEvent timeEvent = new TimeEvent();
        timeEvent.setEmployeeId("00000");
        return "attendance-sheet/index";
    }
}
