package com.example.DevSculpt.dao.alram;

import com.example.DevSculpt.entity.AlarmLogEntity;
import com.example.DevSculpt.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class AlarmDAOImpl implements AlarmDAO {
    private final AlarmRepository repository;

    @Override
    public AlarmLogEntity save(AlarmLogEntity alarmLog) {
        return repository.save(alarmLog);
    }
}
