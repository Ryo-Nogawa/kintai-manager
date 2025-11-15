package com.kintai_manager.app.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai_manager.app.entity.TimeEvent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeEventRepositoryImpl implements TimeEventRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addTimeEvent(TimeEvent timeEvent) {
        System.out.println("Databaseへ登録します。");

        String request_sql = "INSERT INTO time_event "
                + " (employee_id, event_at, event_type, updated_at, updated_employee_id, created_at, created_employee_id) "
                + " VALUES (?, DATE_FORMAT(NOW(), '%Y%m%d%H%i'), ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)";

        String employee_id = timeEvent.getEmployeeId();
        // time_eventテーブルにデータを登録
        jdbcTemplate.update(request_sql,
                employee_id, timeEvent.getEventType(), employee_id, employee_id);
    }
}
