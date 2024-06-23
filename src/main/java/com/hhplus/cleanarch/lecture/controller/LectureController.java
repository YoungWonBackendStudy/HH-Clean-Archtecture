package com.hhplus.cleanarch.lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hhplus.cleanarch.lecture.application.LectureService;

import jakarta.websocket.server.PathParam;

@RestController
public class LectureController {
    @Autowired
    LectureService lectureService;

    @PostMapping("/lectures/apply")
    public boolean apply(
        @RequestBody long userId
    ) {
        return lectureService.apply(userId);
    }

    @GetMapping("/lectures/application/{userId}")
    public boolean userApplied(
        @PathParam("userId") long userId
    ) {
        return lectureService.checkApplication(userId);
    }
}
