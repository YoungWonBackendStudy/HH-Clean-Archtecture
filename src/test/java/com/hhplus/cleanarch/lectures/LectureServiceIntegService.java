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

import com.hhplus.cleanarch.lecture.application.LectureHistoryRepository;
import com.hhplus.cleanarch.lecture.application.LectureService;
import com.hhplus.cleanarch.lecture.application.LectureUserRepository;

@SpringBootTest
public class LectureServiceIntegService {
    @Autowired
    LectureService lectureService;

    @Autowired
    LectureUserRepository lectureUserRepository;

    @Autowired
    LectureHistoryRepository lectureHistoryRepository;

    @Test
    @DisplayName("특강 지원하고 확인하기")
    void testApplyAndCheck() {
        //given
        long testUserId = 0;

        //when
        lectureService.apply(testUserId);
        boolean applyRes = lectureService.checkApplication(testUserId);

        //then
        assertThat(applyRes).isTrue();
    }

    @Test
    @DisplayName("특강 지원하고 내역 확인")
    void testApplyAndHistory() {
        //given
        long testUserId = 0;
        long testCnt = 5;

        //when: 두 사용자가 5번씩 지원할 때
        for(int i = 0; i < testCnt; i++) {
            try{
                lectureService.apply(testUserId);
                lectureService.apply(testUserId + 2);
            } catch(RuntimeException dupApplyException) {
                continue;
            }
        }
        var applyHistory = lectureService.applicationHistory(testUserId);

        //then: 본인의 내역 5개만 조회
        assertThat(applyHistory.size()).isEqualTo(testCnt);
        for(int i = 0; i < testCnt; i++) {
            assertThat(applyHistory.get(i).userId()).isEqualTo(testUserId);
        }
    }


    @Test
    @DisplayName("특정 사용자가 동시에 10번 지원")
    void testConsistentDuplicatedApply() throws InterruptedException {
         //given: id 0의 사용자가
        long userId = 0;
        int executionCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(executionCnt);


        //when: 10번 동시에 지원할 때
        var beforeApply = lectureUserRepository.findAll();
        var historyBefore = lectureHistoryRepository.findByUserId(userId);
        for (int i = 0; i < executionCnt; i++) {
            executorService.submit(() -> {
                try{ lectureService.apply(userId); }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        //then: 1번만 지원 && History는 10개 전부 추가
        var afterApply = lectureUserRepository.findAll();
        assertThat(afterApply.size()).isEqualTo(beforeApply.size() + 1);

        var historyAfter = lectureHistoryRepository.findByUserId(userId);
        assertThat(historyAfter.size()).isEqualTo(historyBefore.size() + executionCnt);
    }

    @Test
    @DisplayName("32명의 사용자가 동시 지원")
    void testConsistentApply() throws InterruptedException {
        //given: 32명의 사용자
        int userCnt = 32;
        List<Long> userIdsToApply = new ArrayList<>(userCnt);
        for(long i = 0; i < userCnt; i++) {
            userIdsToApply.add(i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(userIdsToApply.size());

        //when: 모두 동시에 지원할 때
        var beforeApply = lectureUserRepository.findAll();
        for (Long userId : userIdsToApply) {
            executorService.submit(() -> {
                try{ lectureService.apply(userId); }
                finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        //then: 최대 정원인 30명까지만 지원 됨.
        var afterApply = lectureUserRepository.findAll();
        assertThat(afterApply.size()).isEqualTo(beforeApply.size() + 30);
    }
}
