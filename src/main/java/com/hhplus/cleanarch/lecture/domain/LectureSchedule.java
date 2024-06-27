package com.hhplus.cleanarch.lecture.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LectureSchedule {
    long id;
    long lectureId;
    long dateMillis;
    int maxUser;
    int appliedUserCnt;

    public LectureScheduleWithHistory withHistory(List<LectureScheduleHistory> lectureSchHistories) {
        return new LectureScheduleWithHistory(id, lectureId, dateMillis, maxUser, appliedUserCnt, lectureSchHistories);
    }
}
