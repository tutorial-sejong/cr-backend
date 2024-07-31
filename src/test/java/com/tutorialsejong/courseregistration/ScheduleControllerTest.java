package com.tutorialsejong.courseregistration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ScheduleControllerTest {

    @LocalServerPort
    private int port;

    private String accessToken;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        // 로그인
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("studentId", "230000000");
        loginRequest.put("password", "password123");

        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();
        accessToken = loginResponse.jsonPath().getString("accessToken");
    }

    @Test
    public void whenSearchSchedulesWithoutParams_thenReturnsAllSchedules() {
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/schedules/search")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        List<Map<String, Object>> schedules = response.jsonPath().getList("");
        assertThat(schedules).hasSize(2);
    }

    @Test
    public void whenSearchSchedulesWithParams_thenReturnsFilteredSchedules() {
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("schDeptAlias", "CS")
                .when()
                .get("/schedules/search")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        List<Map<String, Object>> schedules = response.jsonPath().getList("");
        assertThat(schedules).hasSize(1);
        assertThat(schedules.get(0).get("curiNm")).isEqualTo("컴퓨터과학개론");
        assertThat(schedules.get(0).get("schDeptAlias")).isEqualTo("CS");
    }

    @Test
    public void whenSearchSchedulesWithNonExistentParams_thenReturnsNotFound() {
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("schDeptAlias", "NONEXISTENT")
                .when()
                .get("/schedules/search")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void whenSearchSchedulesWithCuriNo_thenReturnsCorrectSchedule() {
        String curiNo = "CS101";
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("curiNo", curiNo)
                .when()
                .get("/schedules/search")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        List<Map<String, Object>> schedules = response.jsonPath().getList("");
        assertThat(schedules).hasSize(1);
        Map<String, Object> schedule = schedules.get(0);
        assertThat(schedule.get("curiNo")).isEqualTo(curiNo);
        assertThat(schedule.get("curiNm")).isEqualTo("컴퓨터과학개론");
    }

    @Test
    public void whenSearchSchedulesWithInvalidParams_thenReturnsBadRequest() {
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("invalidParam", "value")
                .when()
                .get("/schedules/search")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
