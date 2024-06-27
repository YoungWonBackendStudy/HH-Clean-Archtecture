package com.hhplus.cleanarch.lecture.controller;

import com.hhplus.cleanarch.lecture.domain.LectureSchedule;

public class LectureScheduleDto {
    public static record Response (
        long id,
        long dateMillis,
        int maxUserCnt,
        int curUserCnt
    ) {
    }

    public static class Mapper {
        public static Response toDto(LectureSchedule domain) {
            return new Response(
                domain.getId(),
                domain.getDateMillis(),
                domain.getMaxUser(),
                domain.getAppliedUserCnt()
            );
        }
    }
}
