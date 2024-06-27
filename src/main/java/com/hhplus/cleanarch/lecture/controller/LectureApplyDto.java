package com.hhplus.cleanarch.lecture.controller;


public class LectureApplyDto {
    public record Request(
        long lectureId,
        long userId
    ) {
    }
}
