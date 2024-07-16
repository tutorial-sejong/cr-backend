package com.tutorialsejong.courseregistration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

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
        assertThat(response.jsonPath().getString("accessToken")).isNotBlank();
        assertThat(response.jsonPath().getString("refreshToken")).isNotBlank();
    }

    @Test
    public void whenInvalidPassword_thenReturnsNotAcceptable() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("studentId", "230000000");
        loginRequest.put("password", "password123");

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();

        loginRequest = new HashMap<>();
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

    @Test
    public void whenRefreshToken_thenReturnsNewAccessToken() {
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

        String refreshToken = loginResponse.jsonPath().getString("refreshToken");

        Map<String, String> refreshRequest = new HashMap<>();
        refreshRequest.put("refreshToken", refreshToken);

        Response refreshResponse = given()
                .contentType(ContentType.JSON)
                .body(refreshRequest)
                .when()
                .post("/api/auth/refresh")
                .then()
                .extract().response();

        assertThat(refreshResponse.getStatusCode()).isEqualTo(200);
        assertThat(refreshResponse.jsonPath().getString("accessToken")).isNotBlank();
    }

    @Test
    public void whenInvalidRefreshToken_thenReturnsUnauthorized() {
        Map<String, String> refreshRequest = new HashMap<>();
        refreshRequest.put("refreshToken", "invalidRefreshToken");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(refreshRequest)
                .when()
                .post("/api/auth/refresh")
                .then()
                .extract().response();

        assertThat(response.getStatusCode()).isEqualTo(401);
    }
}
