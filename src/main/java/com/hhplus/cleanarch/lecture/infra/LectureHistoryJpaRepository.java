package com.hhplus.cleanarch.lecture.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhplus.cleanarch.lecture.entity.LectureHistoryEntity;

public interface LectureHistoryJpaRepository extends JpaRepository<LectureHistoryEntity, Long>{
    List<LectureHistoryEntity> findByUserId(long userId);
    
}
