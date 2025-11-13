package com.kintai_manager.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kintai_manager/")
public class TopPageController {

    @GetMapping("top")
    public String showTopPage() {
        return "top-page";
    }
}
