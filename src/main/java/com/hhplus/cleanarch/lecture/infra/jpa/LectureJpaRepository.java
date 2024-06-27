package com.hhplus.cleanarch.lecture.infra.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhplus.cleanarch.lecture.infra.entity.LectureEntity;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
    
}