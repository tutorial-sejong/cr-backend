package com.tutorialsejong.courseregistration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void whenValidLogin_thenReturnsToken() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("studentId", "230000000");
        loginRequest.put("password", "password123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("access_token")).isNotBlank();
    }

    @Test
    public void whenInvalidPassword_thenReturnsNotAcceptable() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("studentId", "230000000");
        loginRequest.put("password", "wrongpassword");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(406);
    }

    @Test
    public void whenEmptyCredentials_thenReturnsBadRequest() {
        Map<String, String> loginRequest = new HashMap<>();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(400);
    }
}
