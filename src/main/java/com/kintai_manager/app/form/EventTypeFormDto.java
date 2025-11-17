package com.kintai_manager.app.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventTypeFormDto {

    @NotBlank
    private String eventType;

}