package com.hhplus.cleanarch.lecture.infra;

import com.hhplus.cleanarch.lecture.domain.LectureHistory;
import com.hhplus.cleanarch.lecture.entity.LectureHistoryEntity;

public class LectureHistoryMapper {
    public static LectureHistory toDomain(LectureHistoryEntity entity) {
        return new LectureHistory(entity.getId(), entity.getUserId(), entity.getApplyTimeMillies());
    }
}
