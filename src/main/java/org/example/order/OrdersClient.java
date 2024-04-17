package org.example.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.order.listOrders.ListOrders;
import org.example.rest.RestClient;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestClient {
    private static final String ORDERS_URL = "api/orders";

    @Step("Create order")
    public ValidatableResponse createOrder(Order order, String accessToken){
        return
                given()
                        .spec(getSpec())
                        .auth().oauth2(accessToken).log().all()
                        .body(order)
                        .when()
                        .post(ORDERS_URL).then().log().all();

    }
    @Step("Get list orders")
    public ValidatableResponse getListOrders(String accessToken){
        return
        given()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDERS_URL).then();
    }

    @Step("Output of data JSON to the report")
    @Attachment(value = "Вложение", type = "application/json", fileExtension = ".txt")
    public String outputBodyRequest(String accessToken){
        ListOrders listOrders =
                given()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDERS_URL)
                .body().as(ListOrders.class);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json =gson.toJson(listOrders);
        return json;
    }
}
