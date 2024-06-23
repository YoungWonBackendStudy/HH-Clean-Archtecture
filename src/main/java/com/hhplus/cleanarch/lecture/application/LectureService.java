package com.hhplus.cleanarch.lecture.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hhplus.cleanarch.lecture.domain.LectureHistory;
import com.hhplus.cleanarch.lecture.domain.LectureUser;

@Service
public class LectureService {
    final LectureUserRepository userRepository;
    final LectureHistoryRepository historyRepository;

    public LectureService(LectureUserRepository userRepository, LectureHistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    };

    public boolean apply(long userId) {
        List<LectureUser> users = userRepository.findAll();
        if(users.size() >= 30) throw new RuntimeException("정원을 초과했습니다.");

        LectureUser lectureUser = new LectureUser(0, userId);
        if(users.contains(lectureUser)) throw new RuntimeException("이미 지원한 사용자입니다.");

        userRepository.save(new LectureUser(0, userId));
        return true;
    }

    public boolean checkApplication(long userId) {
        LectureUser lectureUser = userRepository.findByUserId(userId);
        return lectureUser != null && lectureUser.userId() == userId;
    }

    public List<LectureHistory> applicationHistory(long userId) {
        return historyRepository.findByUserId(userId);
    }
}
