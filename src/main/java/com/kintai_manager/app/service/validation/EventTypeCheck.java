package com.kintai_manager.app.service.validation;

import com.kintai_manager.app.enums.EventType;
import com.kintai_manager.app.form.EventTypeFormDto;

public class EventTypeCheck {
    public void checkEventType(EventTypeFormDto eventTypeFormDto) throws IllegalArgumentException {
        EventType.valueOf(eventTypeFormDto.getEventType());
    }
}
