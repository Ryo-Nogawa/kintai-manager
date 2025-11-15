package com.kintai_manager.app.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.service.RegistService;

@Service
@Profile("local")
public class RegistServiceMock implements RegistService {
    @Override
    public void registAttendance(TimeEvent timeEvent) {
        System.out.println("--------------------------------");
        System.out.println("employeeId: " + timeEvent.getEmployeeId() + " eventType: " + timeEvent.getEventType());
        System.out.println("--------------------------------");
    }
}
