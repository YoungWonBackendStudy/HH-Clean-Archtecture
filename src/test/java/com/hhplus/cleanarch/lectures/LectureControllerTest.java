
package com.hhplus.cleanarch.lectures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.hhplus.cleanarch.lecture.controller.LectureApplyDto;
import com.hhplus.cleanarch.lecture.controller.LectureApplyDto.Request;
import com.hhplus.cleanarch.lecture.controller.LectureScheduleDto;
import com.hhplus.cleanarch.lecture.domain.LectureSchedule;
import com.hhplus.cleanarch.lecture.infra.entity.LectureEntity;
import com.hhplus.cleanarch.lecture.infra.entity.LectureScheduleEntity;
import com.hhplus.cleanarch.lecture.infra.entity.LectureSchHistoryEntity;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureSchHistoryJpaRepository;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureJpaRepository;
import com.hhplus.cleanarch.lecture.infra.jpa.LectureScheduleJpaRepository;

import io.restassured.RestAssured;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LectureControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    LectureScheduleJpaRepository lectureScheduleJpaRepository;

    @Autowired
    LectureJpaRepository lectureJpaRepository;

    @Autowired
    LectureSchHistoryJpaRepository lectureHistoryJpaRepository;

    @BeforeEach
    void setUpDB() {
        lectureJpaRepository.deleteAll();
        lectureJpaRepository.save(new LectureEntity(0l, "특강01"));

        lectureScheduleJpaRepository.deleteAll();
        lectureScheduleJpaRepository.save(new LectureScheduleEntity(0l, 0l, 0l, 20, 1));

        lectureHistoryJpaRepository.deleteAll();
        lectureHistoryJpaRepository.save(new LectureSchHistoryEntity(0l, 0l, 0l, 0l));
    }

    @Test
    @DisplayName("특강 조회 > 지원결과(False) > 지원 > 지원결과(True)")
    void testApply() {
        // given
        long testUserId = 1;
        

        //when: /lectures로 조회했을 때
        LectureScheduleDto.Response[] getRes = RestAssured
            .given()
                .accept("application/json")
                .port(port)
            .when()
                .get("/lectures")
            .then()
                .statusCode(200)
                .extract().as(LectureScheduleDto.Response[].class);

        //then: 성공적으로 조회 완료
        assertThat(getRes).isNotNull();
        assertThat(getRes).isNotEmpty();

        //when: /lectures/application/{userId}로 확인했을 때
        boolean applicationRes = RestAssured
            .given()
                .pathParam("userId", testUserId)
                .param("lectureId", getRes[0].id())
                .port(port)
                .accept("application/json")
            .when()
                .get("/lectures/application/{userId}")
            .then()
                .statusCode(200)
                .extract().as(Boolean.class);

        //then: 지원 전에는 False
        assertThat(applicationRes).isFalse();

        //when: /lectures/apply로 지원했을 때
        LectureApplyDto.Request testRequest = new LectureApplyDto.Request(getRes[0].id(), testUserId);
        boolean applyRes = RestAssured
            .given()
                .body(testRequest)
                .port(port)
                .contentType("application/json")
                .accept("application/json")
            .when()
                .post("/lectures/apply")
            .then()
                .statusCode(200)
                .extract().as(Boolean.class);

        //then: 성공적으로 지원 완료
        assertThat(applyRes).isTrue();

        //when: /lectures/application/{userId}로 확인했을 때
        boolean applicationResAfter = RestAssured
            .given()
                .port(port)
                .pathParam("userId", testUserId)
                .param("lectureId", getRes[0].id())
                .accept("application/json")
            .when()
                .get("/lectures/application/{userId}")
            .then()
                .statusCode(200)
                .extract().as(Boolean.class);

        //then: 지원한 후에는 지원완료되었다고 표시
        assertThat(applicationResAfter).isTrue();
    }
}
