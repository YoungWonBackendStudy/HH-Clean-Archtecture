package com.hhplus.cleanarch.lecture.infra.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;

import com.hhplus.cleanarch.lecture.infra.entity.LectureScheduleEntity;

import jakarta.persistence.LockModeType;


public interface LectureScheduleJpaRepository extends JpaRepository<LectureScheduleEntity, Long>{
    @Lock(LockModeType.PESSIMISTIC_WRITE) 
    Optional<LectureScheduleEntity> findAndLockById(@NonNull Long id);
}
