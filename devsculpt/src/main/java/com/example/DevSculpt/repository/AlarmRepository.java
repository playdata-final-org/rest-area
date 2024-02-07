package com.example.DevSculpt.repository;

import com.example.DevSculpt.entity.AlarmLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<AlarmLogEntity, Long> {

}
