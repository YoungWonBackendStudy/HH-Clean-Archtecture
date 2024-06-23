package com.hhplus.cleanarch.lecture.infra;

import com.hhplus.cleanarch.lecture.domain.LectureUser;
import com.hhplus.cleanarch.lecture.entity.LectureUserEntity;

public class LectureUserMapper {
    public static LectureUser toDomain(LectureUserEntity entity) {
        return new LectureUser(entity.getLectureId(), entity.getUserId());
    }

    public static LectureUserEntity toEntity(LectureUser domain) {
        return LectureUserEntity.builder()
            .userId(domain.userId())
            .lectureId(domain.lectureId())
            .build();
    }
}
