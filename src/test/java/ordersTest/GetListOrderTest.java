package ordersTest;

import io.restassured.response.ValidatableResponse;
import org.example.order.OrdersClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetListOrderTest {
    private OrdersClient ordersClient;
    @Before
    public void createTestData(){
        ordersClient = new OrdersClient();
    }
    @Test
    public void getListOrderWithOutAuth(){
        ValidatableResponse response = ordersClient.getListOrders("");

        String message = response.extract().path("message");

        Assert.assertEquals(message,"You should be authorised");
        response.statusCode(401);

    }
}
