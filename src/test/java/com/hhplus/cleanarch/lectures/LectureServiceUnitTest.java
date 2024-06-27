package com.hhplus.cleanarch.lectures;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hhplus.cleanarch.lecture.domain.LectureSchHistoyRepository;
import com.hhplus.cleanarch.lecture.domain.LectureSchRepository;
import com.hhplus.cleanarch.lecture.domain.LectureSchedule;
import com.hhplus.cleanarch.lecture.domain.LectureScheduleHistory;
import com.hhplus.cleanarch.lecture.domain.LectureService;

public class LectureServiceUnitTest {
    LectureService lectureService;
    LectureSchHistoyRepository mockLectureSchHisRepository;
    LectureSchRepository mockLectureSchRepository;

    @BeforeEach
    void setup() {
        mockLectureSchHisRepository = mock(LectureSchHistoyRepository.class);
        mockLectureSchRepository = mock(LectureSchRepository.class);
        lectureService = new LectureService(mockLectureSchRepository, mockLectureSchHisRepository);
    }

    @Test
    @DisplayName("특강 조회하기")
    void testGetLectures() {
        //given
        List<LectureSchedule> lectureSchedules = new ArrayList<>(2);
        lectureSchedules.add(new LectureSchedule(0, 0, 0, 5, 0));
        lectureSchedules.add(new LectureSchedule(1, 0, 0, 3, 0));

        when(mockLectureSchRepository.findAll()).thenReturn(lectureSchedules);

        //when
        var getRes = lectureService.getLectureSchedules();

        //then
        assertThat(getRes).isEqualTo(lectureSchedules);
    }

    @Test
    @DisplayName("특강 지원하기")
    void testApply() {
        //given
        long testLectureId = 0;
        long testUserId = 0;
        LectureSchedule testLectureSchedule = new LectureSchedule(0, testLectureId, 0, 10, 0);
        List<LectureScheduleHistory> lectuerHistories = List.of();

        when(mockLectureSchRepository.selectById(testLectureId)).thenReturn(testLectureSchedule);
        when(mockLectureSchHisRepository.selectByLectureSheduleId(testLectureId)).thenReturn(lectuerHistories);
        when(mockLectureSchHisRepository.save(any(LectureScheduleHistory.class)))
            .thenReturn(new LectureScheduleHistory(0l, testLectureId, testUserId, 0l));

        //when
        boolean applyRes = lectureService.apply(testLectureSchedule.getId(), testUserId);

        //then
        assertThat(applyRes).isTrue();
    }

    @Test
    @DisplayName("지원 여부 확인")
    void testCheckApplication() {
        long testUserId = 0;
        LectureSchedule testLectureSchedule = new LectureSchedule(0, 0, 0, 10, 1);
        List<LectureScheduleHistory> lectuerHistories = new ArrayList<>(1);
        lectuerHistories.add(new LectureScheduleHistory(0l, testLectureSchedule.getId(), testUserId, 0));

        when(mockLectureSchRepository.selectById(testLectureSchedule.getId())).thenReturn(testLectureSchedule);
        when(mockLectureSchHisRepository.selectByLectureSheduleId(testLectureSchedule.getId())).thenReturn(lectuerHistories);

        //when
        boolean checkApplicationRes = lectureService.checkApplication(testLectureSchedule.getId(), testUserId);

        //then
        assertThat(checkApplicationRes).isTrue();
    }

    @Test
    @DisplayName("예외. 정원 10명이 모두 찬 특강에 지원")
    void testOverApply() {
        //given
        long testUserId = 30;
        LectureSchedule testLectureSchedule = new LectureSchedule(0, 0, 0, 10, 10);
        List<LectureScheduleHistory> lectuerHistories = new ArrayList<>(10);
        for(int i = 0; i < testLectureSchedule.getMaxUser(); i++) {
            lectuerHistories.add(
                new LectureScheduleHistory(0l, testLectureSchedule.getId(), i, 0)
            );
        }

        when(mockLectureSchRepository.selectById(testLectureSchedule.getId())).thenReturn(testLectureSchedule);
        when(mockLectureSchHisRepository.selectByLectureSheduleId(testLectureSchedule.getId())).thenReturn(lectuerHistories);
    
        //when
        ThrowableAssert.ThrowingCallable exceptionRes = () -> lectureService.apply(testLectureSchedule.getId(), testUserId);

        //then
        assertThatThrownBy(exceptionRes)
            .isInstanceOf(RuntimeException.class).hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("예외. 중복 사용자 지원")
    void testDuplicatedApply() {
        //given
        long testUserId = 0;
        LectureSchedule testLectureSchedule = new LectureSchedule(0, 0, testUserId, 10, 1);
        List<LectureScheduleHistory> lectuerHistories = new ArrayList<>(1);
        lectuerHistories.add(
            new LectureScheduleHistory(0l, testLectureSchedule.getId(), testUserId, 0)
        );

        when(mockLectureSchRepository.selectById(testLectureSchedule.getId())).thenReturn(testLectureSchedule);
        when(mockLectureSchHisRepository.selectByLectureSheduleId(testLectureSchedule.getId())).thenReturn(lectuerHistories);

        //when
        ThrowableAssert.ThrowingCallable exceptionRes = () -> lectureService.apply(testLectureSchedule.getId() ,testUserId);

        //then
        assertThatThrownBy(exceptionRes)
            .isInstanceOf(RuntimeException.class).hasMessage("이미 지원한 사용자입니다.");
    }
}
