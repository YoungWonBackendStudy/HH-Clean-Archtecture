package com.hhplus.cleanarch.lecture.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhplus.cleanarch.lecture.domain.LectureSchRepository;
import com.hhplus.cleanarch.lecture.domain.LectureSchedule;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureScheduleJpaRepository;
import com.hhplus.cleanarch.lecture.infra.mapper.LectureScheduleMapper;

@Repository
public class LectureSchRepositoryImpl implements LectureSchRepository{
    @Autowired
    LectureScheduleJpaRepository lectureSchRepository;

    @Override
    public List<LectureSchedule> findAll() {
        return lectureSchRepository.findAll().stream()
            .map(LectureScheduleMapper::toDomain)
            .toList();
    }

    @Override
    @Transactional
    public LectureSchedule selectById(long lectureScheduleId) {
        var lectureScheduleOptional = lectureSchRepository.findAndLockById(lectureScheduleId);
        if(!lectureScheduleOptional.isPresent()) return null;

        return LectureScheduleMapper.toDomain(lectureScheduleOptional.get());
    }

    @Override
    public LectureSchedule save(LectureSchedule lectureSchedule) {
        return LectureScheduleMapper.toDomain(lectureSchRepository.save(LectureScheduleMapper.toEntity(lectureSchedule)));
    }
}
