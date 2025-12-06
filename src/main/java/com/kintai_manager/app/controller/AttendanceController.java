package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.entity.TimeEventPrimaryKey;
import com.kintai_manager.app.enums.EventType;
import com.kintai_manager.app.form.EventTypeFormDto;
import com.kintai_manager.app.service.RegistService;
import com.kintai_manager.app.service.validation.EventTypeCheck;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kintai_manager")
public class AttendanceController {

    private final RegistService registService;

    /**
     * 勤怠登録
     *
     * @param eventTypeFormDto
     * @param model
     * @return 勤怠登録完了画面
     */
    @PostMapping("/attendances")
    public String registAttendance(@ModelAttribute EventTypeFormDto eventTypeFormDto, Model model) {
        try {
            EventTypeCheck eventTypeCheck = new EventTypeCheck();
            eventTypeCheck.checkEventType(eventTypeFormDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("不正な勤怠種類です。入力値： " + eventTypeFormDto.getEventType());
        }
        TimeEvent timeEvent = new TimeEvent();
        TimeEventPrimaryKey primaryKey = new TimeEventPrimaryKey();
        primaryKey.setEmployeeId("00000");
        primaryKey.setEventType(eventTypeFormDto.getEventType());
        timeEvent.setPrimaryKey(primaryKey);
        registService.registAttendance(timeEvent);

        model.addAttribute("attendance", EventType.valueOf(eventTypeFormDto.getEventType()).getDisplayName());
        return "attendance-done";
    }
}
