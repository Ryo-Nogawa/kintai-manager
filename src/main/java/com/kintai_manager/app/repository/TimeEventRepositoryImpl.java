package com.kintai_manager.app.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai_manager.app.entity.TimeEvent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeEventRepositoryImpl implements TimeEventRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addTimeEvent(TimeEvent timeEvent) throws DataAccessException {
        String request_sql = "INSERT INTO time_event "
                + " (employee_id, event_at, event_type, updated_at, updated_employee_id, created_at, created_employee_id) "
                + " VALUES (?, DATE_FORMAT(NOW(), '%Y%m%d%H%i'), ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)";

        String employee_id = timeEvent.getEmployeeId();
        // time_eventテーブルにデータを登録
        jdbcTemplate.update(request_sql,
                employee_id, timeEvent.getEventType(), employee_id, employee_id);
    }

    @Override
    public List<TimeEvent> getTimeEvents(String employeeId, String targetMonth) throws DataAccessException {
        // SQL文を作成
        String request_sql = ""
                + "SELECT "
                + "event_type, "
                + "SUBSTRING(event_at, 7, 2) as day, "
                + "CONCAT(SUBSTRING(event_at, 9, 2), ':', SUBSTRING(event_at, 11, 2)) as event_time "
                + "FROM time_event "
                + "WHERE employee_id = ? "
                + "AND event_at LIKE CONCAT(?, '%') "
                + "ORDER BY event_at;";

        // queryForListメソッドでSQL文を実行し、結果MapのListで受け取る
        List<Map<String, Object>> timeEvents = jdbcTemplate.queryForList(request_sql, employeeId, targetMonth);

        // TimeEvent格納用のList作成
        List<TimeEvent> timeEventList = new ArrayList<>();

        // 受け取ったMapのListをfor文で回し、各勤怠データをTimeEventオブジェクトに格納
        for (Map<String, Object> timeEvent : timeEvents) {
            TimeEvent timeEventEntity = new TimeEvent();
            timeEventEntity.setEventType((String) timeEvent.get("event_type").toString());
            timeEventEntity.setDay((String) timeEvent.get("day").toString());
            timeEventEntity.setEventTime((String) timeEvent.get("event_time").toString());
            timeEventList.add(timeEventEntity);
        }
        return timeEventList;
    }
}
