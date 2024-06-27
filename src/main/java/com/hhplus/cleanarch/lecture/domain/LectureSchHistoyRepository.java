package com.hhplus.cleanarch.lecture.domain;

import java.util.List;

public interface LectureSchHistoyRepository {
    List<LectureScheduleHistory> selectByLectureSheduleId(long lectureScheduleId); 
    boolean existsByLectureScheduleAndUser(long lectureScheduleId, long userId);
    LectureScheduleHistory save(LectureScheduleHistory lectureScheduleHistory);
}
