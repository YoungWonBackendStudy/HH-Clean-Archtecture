package com.hhplus.cleanarch.lectures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hhplus.cleanarch.lecture.application.LectureHistoryRepository;
import com.hhplus.cleanarch.lecture.application.LectureService;
import com.hhplus.cleanarch.lecture.application.LectureUserRepository;
import com.hhplus.cleanarch.lecture.domain.LectureHistory;
import com.hhplus.cleanarch.lecture.domain.LectureUser;

public class LectureServiceUnitTest {
    LectureService lectureService;
    LectureUserRepository mockUserRepository;
    LectureHistoryRepository mockHistoryRepository;

    @BeforeEach
    void setup() {
        mockHistoryRepository = mock(LectureHistoryRepository.class);
        mockUserRepository = mock(LectureUserRepository.class);
        lectureService = new LectureService(mockUserRepository, mockHistoryRepository);
    }

    @Test
    @DisplayName("특강 지원하기")
    void testApply() {
        //given
        long testUserId = 0;
        LectureUser testLectureUser = new LectureUser(0, testUserId);
        when(mockUserRepository.findAll()).thenReturn(List.of());
        when(mockUserRepository.save(testLectureUser)).thenReturn(testLectureUser);

        //when
        boolean applyRes = lectureService.apply(testUserId);

        //then
        assertThat(applyRes).isTrue();
    }

    @Test
    @DisplayName("지원 여부 확인")
    void testCheckApplication() {
        long testUserId = 0;
        LectureUser testLectureUser = new LectureUser(0, testUserId);
        when(mockUserRepository.findByUserId(testUserId)).thenReturn(testLectureUser);

        //when
        boolean checkApplicationRes = lectureService.checkApplication(testUserId);

        //then
        assertThat(checkApplicationRes).isTrue();
    }

    @Test
    @DisplayName("지원 내역 조회")
    void testApplicationHistory() {
        //given
        long testUserID = 0;
        List<LectureHistory> expectedHistory = new ArrayList<>(2);
        expectedHistory.add(new LectureHistory(0, testUserID, System.currentTimeMillis()));
        expectedHistory.add(new LectureHistory(1, testUserID, System.currentTimeMillis()));
        when(mockHistoryRepository.findByUserId(testUserID)).thenReturn(expectedHistory);

        //when
        List<LectureHistory> historyRes = lectureService.applicationHistory(testUserID);

        //then
        assertThat(historyRes).isEqualTo(expectedHistory);
    }

    @Test
    @DisplayName("예외. 정원 30명 초과 지원")
    void testOverApply() {
        //given
        long testUserId = 0;
        LectureUser testLectureUser = new LectureUser(0, testUserId);

        List<LectureUser> appliers = new ArrayList<>(30);
        for(int i = 1; i <= 31; i++) {
            appliers.add(new LectureUser(0, i));
        }
        when(mockUserRepository.findAll()).thenReturn(appliers);
        when(mockUserRepository.save(testLectureUser)).thenReturn(testLectureUser);

        //when
        ThrowableAssert.ThrowingCallable exceptionRes = () -> lectureService.apply(testUserId);

        //then
        assertThatThrownBy(exceptionRes)
            .isInstanceOf(RuntimeException.class).hasMessage("정원을 초과했습니다.");
    }

    @Test
    @DisplayName("예외. 중복 지원")
    void testDuplicatedApply() {
        //given
        long testUserId = 0;
        LectureUser testLectureUser = new LectureUser(0, testUserId);

        List<LectureUser> appliers = new ArrayList<>(1);
        appliers.add(new LectureUser(0, testUserId));
        when(mockUserRepository.findAll()).thenReturn(appliers);
        when(mockUserRepository.save(testLectureUser)).thenReturn(testLectureUser);

        //when
        ThrowableAssert.ThrowingCallable exceptionRes = () -> lectureService.apply(testUserId);

        //then
        assertThatThrownBy(exceptionRes)
            .isInstanceOf(RuntimeException.class).hasMessage("이미 지원한 사용자입니다.");
    }
}
