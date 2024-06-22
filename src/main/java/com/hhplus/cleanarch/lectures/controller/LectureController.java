package com.hhplus.cleanarch.lectures.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
public class LectureController {

    @PostMapping("/lectures/apply")
    public boolean apply(
        @RequestBody long userId
    ) {
        return false;
    }

    @GetMapping("/lectures/application/{userId}")
    public boolean userApplied(
        @PathParam("userId") long userId
    ) {
        return false;
    }
}
