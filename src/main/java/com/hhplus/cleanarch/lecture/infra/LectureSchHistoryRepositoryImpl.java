package com.hhplus.cleanarch.lecture.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hhplus.cleanarch.lecture.domain.LectureSchHistoyRepository;
import com.hhplus.cleanarch.lecture.domain.LectureScheduleHistory;
import com.hhplus.cleanarch.lecture.infra.entity.LectureSchHistoryEntity;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureSchHistoryJpaRepository;
import com.hhplus.cleanarch.lecture.infra.mapper.LectureSchHistoryMapper;

@Repository
public class LectureSchHistoryRepositoryImpl implements LectureSchHistoyRepository {
    @Autowired
    LectureSchHistoryJpaRepository lectureSchHisRepository;

    @Override
    public boolean existsByLectureScheduleAndUser(long lectureScheduleId, long userId) {
        return lectureSchHisRepository.existsByLectureScheduleIdAndUserId(lectureScheduleId, userId);
    }

    @Override
    public List<LectureScheduleHistory> selectByLectureSheduleId(long lectureScheduleId) {
        return lectureSchHisRepository.findByLectureScheduleId(lectureScheduleId).stream()
            .map(LectureSchHistoryMapper::toDomain)
            .toList();
    }

    @Override
    public LectureScheduleHistory save(LectureScheduleHistory lectureScheduleHistory) {
        LectureSchHistoryEntity saveRes =  lectureSchHisRepository.save(LectureSchHistoryMapper.toEntity(lectureScheduleHistory));
        return LectureSchHistoryMapper.toDomain(saveRes);
    }
    
}
