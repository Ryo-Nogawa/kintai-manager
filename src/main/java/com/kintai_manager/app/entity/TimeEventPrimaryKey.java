package com.kintai_manager.app.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TimeEventPrimaryKey implements Serializable {
    @Column(nullable = false, length = 8)
    private String eventDay;

    @Column(nullable = false, length = 5)
    private String employeeId;

    @Column(nullable = false, length = 255)
    private String eventType;

    @Column(nullable = false, length = 1)
    private Integer repeatNo;
}
