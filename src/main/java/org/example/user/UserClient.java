package org.example.user;

import io.restassured.response.ValidatableResponse;
import org.example.rest.RestClient;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String REGISTER_PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String DELETE_PATH = "api/auth/user";
    private static final String LOGOUT_PATH = "api/auth/logout";


    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH).then().log().all();

    }

    public ValidatableResponse login(UserCredentials credentials) {
        return
                given()
                        .spec(getSpec())
                        .body(credentials)
                        .when()
                        .post(LOGIN_PATH).then();

    }

    public ValidatableResponse delete(String accessToken) {
        return
                given()
                        .spec(getSpec())
                        .auth().oauth2(accessToken)
                        .when()
                        .delete(DELETE_PATH).then().log().all();
    }

    public ValidatableResponse logout(String refreshToken) {
        return
                given()
                        .spec(getSpec())
                        .body(refreshToken)
                        .when()
                        .post(LOGOUT_PATH).then();

    }

    public ValidatableResponse update(String token, User user) {
        return
                given()
                        .spec(getSpec())
                        .auth().oauth2(token).log().all()
                        .when()
                        .body(user)
                        .patch(DELETE_PATH).then().log().all();

    }
}
