# kintai-manager

## テーブル設計

### time_event 定義

| カラム名            | データ型     | 制約     | Key                      |
| ------------------- | ------------ | -------- | ------------------------ |
| event_day           | char(8)      | NOT NULL | PRIMARY KEY              |
| employee_id         | char(5)      | NOT NULL | PRIMARY KEY, FOREIGN KEY |
| event_type          | VARCHAR(255) | NOT NULL | PRIMARY KEY              |
| repeat_no           | int(1)       | NOT NULL | PRIMARY KEY              |
| event_at            | char(12)     | NOT NULL |                          |
| is_correction       | tinyint(1)   | NOT NULL |                          |
| reason              | VARCHAR(255) | NULL     |                          |
| updated_at          | timestamp(3) | NOT NULL |                          |
| updated_employee_id | char(5)      | NOT NULL |                          |
| created_at          | timestamp(3) | NOT NULL |                          |
| created_employee_id | char(5)      | NOT NULL |                          |

```sql
CREATE TABLE time_event (
    event_day           CHAR(8)        NOT NULL,
    employee_id         CHAR(5)        NOT NULL,
    event_type          VARCHAR(255)   NOT NULL,
    repeat_no           INT(1)         NOT NULL,
    event_at            CHAR(12)       NOT NULL,
    is_correction       TINYINT(1)     NOT NULL,
    reason              VARCHAR(255)   NULL,
    updated_at          TIMESTAMP(3)   NOT NULL,
    updated_employee_id CHAR(5)        NOT NULL,
    created_at          TIMESTAMP(3)   NOT NULL,
    created_employee_id CHAR(5)        NOT NULL,

    -- 複合主キー
    PRIMARY KEY (event_day, employee_id, event_type, repeat_no),

    -- 外部キー（必要に応じて参照先テーブル名を変更）
    CONSTRAINT fk_time_event_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(employee_id)
);
```

```sql
ALTER TABLE time_event
    MODIFY COLUMN is_correction TINYINT(1) NOT NULL DEFAULT 0;
```

### time_event インデックス

| インデックス名   | 種別        | カラム                                        | 用途／備考 |
| ---------------- | ----------- | --------------------------------------------- | ---------- |
| PK_time_event    | PRIMARY KEY | event_day, employee_id, event_type, repeat_no | 主キー     |
| IX_time_event_01 | NON_UNIQUE  | employee_id                                   | 外部キー   |

```sql
-- 非ユニークインデックス（外部キー用）
CREATE INDEX IX_time_event_01
    ON time_event (employee_id);
```
