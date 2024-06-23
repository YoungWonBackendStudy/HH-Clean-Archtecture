
package com.hhplus.cleanarch.lectures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;


@SpringBootTest
public class LectureControllerTest {
    @Test
    @DisplayName("POST /lectures/apply 테스트")
    void testApply() {
        // given
        long testUserId = 1;

        //when: /lectures/apply로 지원했을 때
        boolean applyRes = RestAssured
            .given()
                .body(testUserId)
                .accept("application/json")
            .when()
                .post("/lectures/apply")
            .then()
                .statusCode(200)
                .extract().as(Boolean.class);

        //then: 성공적으로 지원 완료
        assertThat(applyRes).isTrue();

        //when: /lectures/application/{userId}로 확인했을 때
        boolean applicationRes = RestAssured
            .given()
                .pathParam("userId", testUserId)
                .accept("application/json")
            .when()
                .get("/lectures/application/{userId}")
            .then()
                .statusCode(200)
                .extract().as(Boolean.class);

        //then: 이전 지원 결과와 동일
        assertThat(applicationRes).isEqualTo(applyRes);
    }
}
