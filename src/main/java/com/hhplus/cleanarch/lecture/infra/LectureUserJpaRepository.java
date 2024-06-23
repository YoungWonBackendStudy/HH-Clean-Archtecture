package com.hhplus.cleanarch.lecture.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hhplus.cleanarch.lecture.entity.LectureUserEntity;

public interface LectureUserJpaRepository extends JpaRepository<LectureUserEntity, Long>{
    LectureUserEntity findByUserId(long userId);
}
