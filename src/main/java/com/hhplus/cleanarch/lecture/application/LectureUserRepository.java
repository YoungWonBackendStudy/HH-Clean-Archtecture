package com.hhplus.cleanarch.lecture.application;

import java.util.List;

import com.hhplus.cleanarch.lecture.domain.LectureUser;

public interface LectureUserRepository {
    List<LectureUser> findAll();
    LectureUser findByUserId(long userId);
    LectureUser save(LectureUser lecture);
}
