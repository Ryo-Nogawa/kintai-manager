package com.kintai_manager.app.repository;

import java.util.List;

import com.kintai_manager.app.entity.TimeEvent;

public interface TimeEventRepository {
    void addTimeEvent(TimeEvent timeEvent);

    List<TimeEvent> getTimeEvents(String employeeId, String targetMonth);
}
