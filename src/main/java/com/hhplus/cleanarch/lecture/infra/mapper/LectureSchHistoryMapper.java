package com.hhplus.cleanarch.lecture.infra.mapper;

import com.hhplus.cleanarch.lecture.domain.LectureScheduleHistory;
import com.hhplus.cleanarch.lecture.infra.entity.LectureSchHistoryEntity;

public class LectureSchHistoryMapper {
    public static LectureScheduleHistory toDomain(LectureSchHistoryEntity entity) {
        return new LectureScheduleHistory(
            entity.getId(), 
            entity.getLectureScheduleId(),
            entity.getUserId(), 
            entity.getUpdateMillis()
        );
    }

    public static LectureSchHistoryEntity toEntity(LectureScheduleHistory domain) {
        return new LectureSchHistoryEntity(
            domain.getId(),
            domain.getLectureScheduleId(),
            domain.getUserId(),
            domain.getUpdateMillis()
        );
    }
}
