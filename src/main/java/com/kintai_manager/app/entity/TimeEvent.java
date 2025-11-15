package com.kintai_manager.app.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Table(name = "time_events")
@Data
public class TimeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeEventId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private String employeeId;

    @Column(nullable = false, length = 12)
    private String eventAt;

    @Column(nullable = false, length = 255)
    private String eventType;

    @Column(nullable = false)
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
