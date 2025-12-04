package com.kintai_manager.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Table(name = "time_event")
@Data
public class TimeEvent {
    @Column(nullable = false, length = 8)
    private String eventDay;

    @Column(nullable = false, length = 5)
    private String employeeId;

    @Column(nullable = false, length = 255)
    private String eventType;

    @Column(nullable = false, length = 1)
    private Integer repeatNo;

    @Column(nullable = false, length = 12)
    private String eventAt;

    @Column(nullable = false, length = 1)
    private Boolean isCorrection;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, length = 5)
    private String updatedEmployeeId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 5)
    private String createdEmployeeId;
}
