package ordersTest;

import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderGenerator;
import org.example.order.OrdersClient;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetListOrderTest {
    private OrdersClient ordersClient;
    private Order order;
    private UserClient userClient;
    private String accessToken;
    private User user;
    @Before
    public void createTestData(){
        ordersClient = new OrdersClient();
        userClient = new UserClient();
    }
    @After
    public void cleanUp() {
        if (accessToken != null)
            userClient.delete(accessToken);
    }
    @Test
    public void getListOrderWithOutAuth(){
        ValidatableResponse response = ordersClient.getListOrders("");

        String message = response.extract().path("message");

        Assert.assertEquals(message,"You should be authorised");
        response.statusCode(401);

    }

    @Test
    public void getListOrderWithAuth(){
        //создания пользователя
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        //получение токена
        accessToken = response.extract().path("accessToken");

        StringBuilder sb = new StringBuilder(accessToken);
        sb.delete(0,7);
        accessToken = sb.toString();

        //создание заказа под авторизованным пользователем
        order = OrderGenerator.withIngredients();
        ValidatableResponse validatableResponse = ordersClient.createOrder(order, accessToken);

        //получение списка заказа под созданным пользователем
        boolean success = ordersClient.createOrder(order,accessToken).extract().path("success");
        Assert.assertTrue(success);

        //вывод списка заказа в отчет
        ordersClient.outputBodyRequest(accessToken);
    }


}
