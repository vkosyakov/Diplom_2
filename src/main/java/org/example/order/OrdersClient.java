package org.example.order;

import io.restassured.response.ValidatableResponse;
import org.example.rest.RestClient;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestClient {
    private static final String ORDERS_URL = "api/orders";

    public ValidatableResponse createOrder(Order order, String accessToken){
        return
                given()
                        .spec(getSpec())
                        .auth().oauth2(accessToken).log().all()
                        .body(order)
                        .when()
                        .post(ORDERS_URL).then().log().all();

    }
    public ValidatableResponse getListOrders(String accessToken){
        return
        given()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDERS_URL).then();
    }
}
