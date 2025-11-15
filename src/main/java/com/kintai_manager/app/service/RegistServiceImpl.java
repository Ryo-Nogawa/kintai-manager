package com.kintai_manager.app.service;

import org.springframework.stereotype.Service;

import com.kintai_manager.app.entity.TimeEvent;
import com.kintai_manager.app.repository.TimeEventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistServiceImpl implements RegistService {

    private final TimeEventRepository timeEventRepository;

    @Override
    public void registAttendance(TimeEvent timeEvent) {
        timeEventRepository.addTimeEvent(timeEvent);
    }
}
