package com.kintai_manager.app.service.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class TargetMonthCheck {
    public void checkTargetMonth(String targetMonth) {
        DateTimeFormatter.ofPattern("uuuuMM").withResolverStyle(ResolverStyle.STRICT).parse(targetMonth);
    }
}
