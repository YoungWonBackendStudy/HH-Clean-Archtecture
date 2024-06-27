package com.hhplus.cleanarch.lecture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hhplus.cleanarch.lecture.domain.LectureService;


@RestController
public class LectureController {
    @Autowired
    LectureService lectureService;

    @PostMapping("/lectures/apply")
    public boolean apply(
        @RequestBody LectureApplyDto.Request lectureApplyRequest
    ) {
        return lectureService.apply(lectureApplyRequest.lectureId(), lectureApplyRequest.userId());
    }

    @GetMapping("/lectures/application/{userId}")
    public boolean userApplied(
        @PathVariable("userId") Long userId,
        @RequestParam("lectureId") long lectureId
    ) {
        return lectureService.checkApplication(lectureId, userId);
    }

    @GetMapping("/lectures")
    public List<LectureScheduleDto.Response> getLectures() {
        return lectureService.getLectureSchedules().stream().map(LectureScheduleDto.Mapper::toDto).toList();
    }
}
