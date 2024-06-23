package com.hhplus.cleanarch.lecture.application;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hhplus.cleanarch.lecture.domain.LectureHistory;

public interface LectureHistoryRepository {
    List<LectureHistory> findByUserId(long userId);
}
