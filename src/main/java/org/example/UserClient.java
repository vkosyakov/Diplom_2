package org.example;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String REGISTER_PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String DELETE_PATH = "api/auth/user";

    private static final String LOGOUT_PATH = "api/auth/logout";

    public ValidatableResponse create(User user){
        return  given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH).then();
    }

    public ValidatableResponse login(UserCredentials credentials){
        return
                given()
                        .spec(getSpec())
                        .body(credentials)
                        .when()
                        .post(LOGIN_PATH).then();

    }

    public ValidatableResponse delete(String token){
        return
                given()
                        .spec(getSpec())
                        .auth().oauth2(token)
                        .when()
                        .delete(DELETE_PATH).then();
    }

    public ValidatableResponse logout(String refreshToken){
        return
                given()
                        .spec(getSpec())
                        .body(refreshToken)
                        .when()
                        .post(LOGOUT_PATH).then();

    }

}
