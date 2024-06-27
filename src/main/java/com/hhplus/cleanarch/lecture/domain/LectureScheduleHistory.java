package com.hhplus.cleanarch.lecture.domain;

import lombok.Getter;

@Getter
public class LectureScheduleHistory {
    Long id;
    long lectureScheduleId;
    long userId;
    long updateMillis;

    public LectureScheduleHistory(long lectureScheduleId, long userId, long updateMillis) {
        this.id = null;
        this.lectureScheduleId = lectureScheduleId;
        this.userId = userId;
        this.updateMillis = updateMillis;
    }

    public LectureScheduleHistory(Long id, long lectureScheduleId, long userId, long updateMillis) {
        this.id = id;
        this.lectureScheduleId = lectureScheduleId;
        this.userId = userId;
        this.updateMillis = updateMillis;
    }
}
