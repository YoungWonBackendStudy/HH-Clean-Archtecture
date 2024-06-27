package com.hhplus.cleanarch.lecture.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LectureService {
    final LectureSchRepository lectureSchRepository;
    final LectureSchHistoyRepository lectureSchHisRepository;

    public LectureService(LectureSchRepository lectureScheduleRepository, LectureSchHistoyRepository lectureSchHisRepository) {
        this.lectureSchRepository = lectureScheduleRepository;
        this.lectureSchHisRepository = lectureSchHisRepository;
    };

    @Transactional
    public boolean apply(long lectureScheduleId, long userId) {
        LectureSchedule lectureSchedule = lectureSchRepository.selectById(lectureScheduleId);
        var LectureScheduleWithHistory = lectureSchedule.withHistory(lectureSchHisRepository.selectByLectureSheduleId(lectureScheduleId));
        var newLectureHistory = LectureScheduleWithHistory.apply(userId);
        
        lectureSchRepository.save(LectureScheduleWithHistory);
        return lectureSchHisRepository.save(newLectureHistory) != null;
    }

    public boolean checkApplication(long lectureScheduleId, long userId) {        
        LectureSchedule lectureSchedule = lectureSchRepository.selectById(lectureScheduleId);
        var LectureScheduleWithHistory = lectureSchedule.withHistory(lectureSchHisRepository.selectByLectureSheduleId(lectureScheduleId));

        return LectureScheduleWithHistory.hasUserApplied(userId);
    }

    public List<LectureSchedule> getLectureSchedules() {
        return lectureSchRepository.findAll();
    }
}
