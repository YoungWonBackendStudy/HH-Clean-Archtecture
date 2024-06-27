package com.hhplus.cleanarch.lecture.infra.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhplus.cleanarch.lecture.infra.entity.LectureSchHistoryEntity;

public interface LectureSchHistoryJpaRepository extends JpaRepository<LectureSchHistoryEntity, Long>{
    Boolean existsByLectureScheduleIdAndUserId(long lectureScheduleId, long userId);
    List<LectureSchHistoryEntity> findByLectureScheduleId(long lectureScheduleId);
}
