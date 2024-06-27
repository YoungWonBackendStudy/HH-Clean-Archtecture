package com.hhplus.cleanarch.lecture.infra.mapper;

import com.hhplus.cleanarch.lecture.domain.LectureSchedule;
import com.hhplus.cleanarch.lecture.infra.entity.LectureScheduleEntity;

public class LectureScheduleMapper {
    public static LectureSchedule toDomain(LectureScheduleEntity lectureScheduleEntity) {
        return new LectureSchedule(
            lectureScheduleEntity.getId(), 
            lectureScheduleEntity.getLectureId(), 
            lectureScheduleEntity.getDateMillis(),
            lectureScheduleEntity.getMaxUserCnt(),
            lectureScheduleEntity.getAppliedUserCnt()
        );
    }

    public static LectureScheduleEntity toEntity(LectureSchedule lectureSchedule) {
        return new LectureScheduleEntity(
            lectureSchedule.getId(),
            lectureSchedule.getLectureId(),
            lectureSchedule.getDateMillis(),
            lectureSchedule.getMaxUser(),
            lectureSchedule.getAppliedUserCnt()
        );
    }
}
