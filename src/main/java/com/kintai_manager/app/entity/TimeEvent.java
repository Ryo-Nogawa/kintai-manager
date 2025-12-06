package com.kintai_manager.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "time_event")
@Data
public class TimeEvent {
    @EmbeddedId
    private TimeEventPrimaryKey primaryKey;

    @Column(nullable = false, length = 12)
    private String eventAt;

    @Column(nullable = false, length = 1)
    private Boolean isCorrection;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false, length = 5)
    private String updatedEmployeeId;

    @Column(nullable = false, length = 5)
    private String createdEmployeeId;
}
