package com.hhplus.cleanarch.lectures.domain;

import java.util.List;

public interface LectureHistoryRepository {
    List<LectureHistory> selectByUserId(long userId);
}
