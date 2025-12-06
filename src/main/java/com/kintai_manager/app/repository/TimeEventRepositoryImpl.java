package com.kintai_manager.app.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.enums.EventType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TimeEventRepositoryImpl implements TimeEventRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${kintai-event.work.threshold}")
    private int kintaiEventWorkThreshold;
    @Value("${kintai-event.break.threshold}")
    private int kintaiEventBreakThreshold;

    @Override
    public void addTimeEvent(TimeEvent timeEvent) throws DataAccessException {
        // 同じ日時に同じ勤怠が存在するかチェック
        String check_same_event_sql = "SELECT COUNT(*) FROM time_event "
                + "WHERE event_day = ? AND employee_id = ? AND event_type = ? ";

        String employee_id = timeEvent.getEmployeeId();
        String event_type = timeEvent.getEventType();

        // 現在日時取得
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMddHHmm");
        String now_datetime = formatter.format(now);
        String today_datetime = now_datetime.substring(0, 8);

        int result_check_same_event = jdbcTemplate.queryForObject(check_same_event_sql, Integer.class, today_datetime,
                employee_id, event_type);

        if (event_type.equals(EventType.WORK_START.name()) || event_type.equals(EventType.WORK_END.name())) {
            if (result_check_same_event > kintaiEventWorkThreshold) {
                throw new IllegalStateException("本日の" + event_type + "はすでに登録されています。" + "登録済み件数："
                        + result_check_same_event + " 最大登録件数：" + kintaiEventWorkThreshold);
            }
        } else if (event_type.equals(EventType.BREAK_START.name()) || event_type.equals(EventType.BREAK_END.name())) {
            if (result_check_same_event > kintaiEventBreakThreshold) {
                throw new IllegalStateException("本日の" + event_type + "はすでに登録されています。" + "登録済み件数："
                        + result_check_same_event + " 最大登録件数：" + kintaiEventBreakThreshold);
            }
        }

        // count結果に+1してrepeat_noを算出
        int sum_repeat_no = result_check_same_event + 1;

        String request_sql = "INSERT INTO time_event "
                + " (event_day, employee_id, event_type, repeat_no, event_at, updated_at, updated_employee_id, created_at, created_employee_id) "
                + " VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)";

        // time_eventテーブルにデータを登録
        jdbcTemplate.update(
                request_sql, today_datetime, employee_id, event_type, sum_repeat_no, now_datetime, employee_id,
                employee_id);
    }

    @Override
    public List<TimeEvent> getTimeEvent(String employeeId, String targetMonth) throws DataAccessException {
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
