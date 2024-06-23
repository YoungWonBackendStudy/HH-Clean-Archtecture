package com.hhplus.cleanarch.lecture.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hhplus.cleanarch.lecture.application.LectureUserRepository;
import com.hhplus.cleanarch.lecture.domain.LectureUser;
import com.hhplus.cleanarch.lecture.entity.LectureUserEntity;

@Repository
public class LectureUserRepositoryImpl implements LectureUserRepository{
    @Autowired
    LectureUserJpaRepository lectureJpaRepository;

    @Override
    public List<LectureUser> findAll() {
        return lectureJpaRepository.findAll().stream()
            .map(LectureUserMapper::toDomain)
            .toList();
    }

    @Override
    public LectureUser save(LectureUser lectureUser) {
        LectureUserEntity entity = lectureJpaRepository.save(LectureUserMapper.toEntity(lectureUser));
        return LectureUserMapper.toDomain(entity);
    }

    @Override
    public LectureUser findByUserId(long userId) {
        LectureUserEntity entity = lectureJpaRepository.findByUserId(userId);
        return LectureUserMapper.toDomain(entity);
    }
}
