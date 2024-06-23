package com.hhplus.cleanarch.lecture.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hhplus.cleanarch.lecture.application.LectureHistoryRepository;
import com.hhplus.cleanarch.lecture.domain.LectureHistory;

@Repository
public class LectureHistoryRepositoryImpl implements LectureHistoryRepository {
    @Autowired
    LectureHistoryJpaRepository lectureHistoryJpaRepository;

    @Override
    public List<LectureHistory> findByUserId(long userId) {
        return lectureHistoryJpaRepository.findByUserId(userId).stream()
            .map(LectureHistoryMapper::toDomain)
            .toList();
    }

}
