package com.kintai_manager.app.mock;

import org.springframework.stereotype.Service;

import com.kintai_manager.app.service.RegistService;

@Service
public class RegistServiceMock implements RegistService {
    @Override
    public void registAttendance(String employee_id, String event_type) {
        System.out.println("--------------------------------");
        System.out.println("employeeId: " + employee_id + " eventType: " + event_type);
        System.out.println("--------------------------------");
    }
}
