package com.hhplus.cleanarch.lectures;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hhplus.cleanarch.lecture.domain.LectureSchHistoyRepository;
import com.hhplus.cleanarch.lecture.domain.LectureSchRepository;
import com.hhplus.cleanarch.lecture.domain.LectureSchedule;
import com.hhplus.cleanarch.lecture.domain.LectureService;
import com.hhplus.cleanarch.lecture.infra.entity.LectureScheduleEntity;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureSchHistoryJpaRepository;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureScheduleJpaRepository;

@SpringBootTest
public class LectureServiceIntegTest {
    @Autowired
    LectureService lectureService;

    @Autowired
    LectureSchRepository lectureScheduleRepository;

    @Autowired
    LectureSchHistoyRepository lectureSchHisRepository;

    @Autowired
    LectureScheduleJpaRepository lectureScheduleJpaRepository;

    @Autowired
    LectureSchHistoryJpaRepository lectureSchHisJpaRepository;

    @BeforeEach
    void setup() {
        lectureScheduleJpaRepository.deleteAll();
        lectureSchHisJpaRepository.deleteAll();
        lectureScheduleJpaRepository.save(new LectureScheduleEntity(1l, 0l, 0l, 30, 0));
    }

    @Test
    @DisplayName("특강 조회/지원/확인하기")
    void testApplyAndCheck() {
        //given
        long testUserId = 0;

        //when
        var lectures = lectureService.getLectureSchedules();
        assertThat(lectures.size()).isGreaterThan(0);

        var applyRes = lectureService.apply(lectures.get(0).getId(), testUserId);
        assertThat(applyRes).isTrue();

        boolean checkRes = lectureService.checkApplication(lectures.get(0).getId(), testUserId);

        //then
        assertThat(checkRes).isTrue();
    }

    @Test
    @DisplayName("특정 사용자가 동시에 10번 지원")
    void testConsistentDuplicatedApply() throws InterruptedException {
         //given: id 0의 사용자가
        long lectureId = 1;
        long userId = 0;
        int executionCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(executionCnt);


        //when: 10번 동시에 지원할 때
        var beforeApply = lectureSchHisRepository.selectByLectureSheduleId(lectureId);
        for (int i = 0; i < executionCnt; i++) {
            executorService.submit(() -> {
                try{ lectureService.apply(lectureId, userId); }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        //then: 1번만 지원
        var afterApply = lectureSchHisRepository.selectByLectureSheduleId(lectureId);
        assertThat(afterApply.size()).isEqualTo(beforeApply.size() + 1);
    }

    @Test
    @DisplayName("정원의 2배 인원이 동시지원")
    void testConsistentApply() throws InterruptedException {
        //given: 2배의 사용자
        long lectureId = 1;
        LectureSchedule testLecture = lectureScheduleRepository.selectById(lectureId);

        int userCnt = testLecture.getMaxUser() * 2;
        List<Long> userIdsToApply = new ArrayList<>(userCnt);
        for(long i = 0; i < userCnt; i++) {
            userIdsToApply.add(i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(userIdsToApply.size());

        //when: 모두 동시에 지원할 때
        for (Long userId : userIdsToApply) {
            executorService.submit(() -> {
                try{ lectureService.apply(lectureId, userId); }
                finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        //then: 최대 정원까지만 지원 됨.
        var afterApply = lectureSchHisRepository.selectByLectureSheduleId(lectureId);
        assertThat(afterApply.size()).isEqualTo(testLecture.getMaxUser());
    }
}
