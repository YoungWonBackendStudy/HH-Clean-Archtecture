package com.hhplus.cleanarch.lecture.infra.mapper;

import com.hhplus.cleanarch.lecture.domain.Lecture;
import com.hhplus.cleanarch.lecture.infra.entity.LectureEntity;

public class LectureMapper {
    public static Lecture toDomain(LectureEntity lectureEntity) {
        return new Lecture(lectureEntity.getId(), lectureEntity.getName());
    }

    public static LectureEntity toEntity(Lecture lectureDomain) {
        return new LectureEntity(lectureDomain.getId(), lectureDomain.getName());
    }
}
