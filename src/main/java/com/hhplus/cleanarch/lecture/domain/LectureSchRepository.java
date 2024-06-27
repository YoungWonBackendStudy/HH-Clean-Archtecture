package com.hhplus.cleanarch.lecture.domain;

import java.util.List;

public interface LectureSchRepository {
    List<LectureSchedule> findAll();
    LectureSchedule selectById(long lectureScheduleId);
    LectureSchedule save(LectureSchedule lectureSchedule);
}
