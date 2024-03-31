package ordersTest;

import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderGenerator;
import org.example.order.OrdersClient;
import org.example.rest.RestClient;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {
    private Order order;
    private OrdersClient ordersClient;
    private UserClient userClient;

    private User user;

   @Before
    public void createTestData(){
        ordersClient = new OrdersClient();
        userClient = new UserClient();
    }

    //создание заказа без авторизации
    @Test
    public void createOrderWithOutAuth(){
        order = OrderGenerator.withIngredients();
        ValidatableResponse response = ordersClient.createOrder(order,"");
        response.assertThat().body("order.number",notNullValue()).and().statusCode(200);
    }

    //создание заказа без ингредиентов
  @Test
    public void createOrderWithOutIngredients(){
        order = OrderGenerator.withIngredients();
        order.setIngredients(null);

        ValidatableResponse response = ordersClient.createOrder(order,"");
        String message = response.extract().path("message");

        response.statusCode(400);
        Assert.assertEquals(message,"Ingredient ids must be provided");
    }

    //Создание заказа с авторизацией с ингредиентами
    @Test
    public void createOrderWithAuthAndIngredients(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        String accessToken = response.extract().path("accessToken");

        StringBuilder sb = new StringBuilder(accessToken);
        sb.delete(0,6);
        accessToken = sb.toString();


        order = OrderGenerator.withIngredients();
        ValidatableResponse validatableResponse = ordersClient.createOrder(order, accessToken);
        boolean success = response.extract().path("success");
        validatableResponse.statusCode(200);
        Assert.assertTrue(success);
    }

    //Создание заказа неверным хэшом
    @Test
    public void createOrderWithFailedHash(){
        order = OrderGenerator.invalidHash();
        ValidatableResponse response = ordersClient.createOrder(order,"");
        response.statusCode(500);
    }

}
