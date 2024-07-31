package com.tutorialsejong.courseregistration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.tutorialsejong.courseregistration.registration.repository.CourseRegistrationRepository;
import com.tutorialsejong.courseregistration.schedule.entity.Schedule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CourseRegistrationControllerTest {

    @LocalServerPort
    private int port;

    private String accessToken;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        courseRegistrationRepository.deleteAll();

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
    public void whenRegisterCourse_thenReturnsCreated() {
        Long scheduleId = 1L;
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/registrations/" + scheduleId)
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void whenRegisterCourseAgain_thenReturnsConflict() {
        Long scheduleId = 1L;
        // 첫 번째 등록
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/registrations/" + scheduleId);

        // 두 번째 등록 시도
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/registrations/" + scheduleId)
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    public void whenGetRegisteredCourses_thenReturnsOkAndCorrectSize() {
        Long scheduleId1 = 1L;
        Long scheduleId2 = 2L;
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/registrations/" + scheduleId1);
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/registrations/" + scheduleId2);

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/registrations")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        List<Schedule> registrations = response.jsonPath().getList("", Schedule.class);
        assertThat(registrations).hasSize(2);
        assertThat(registrations).extracting("scheduleId").containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    public void whenGetRegisteredCoursesForStudentWithNoRegistrations_thenReturnsNoContent() {
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/registrations")
                .then()
                .extract().response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void whenCancelRegisteredCourse_thenReturnsOk() {
        Long scheduleId = 1L;

        // 먼저 수강 신청
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/registrations/" + scheduleId)
                .then()
                .extract().response();

        // 수강 취소
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/registrations/" + scheduleId)
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void whenCancelNotRegisteredCourse_thenReturnsNotFound() {
        Long notRegisteredScheduleId = 999L;

        Response response = given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/registrations/" + notRegisteredScheduleId)
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
