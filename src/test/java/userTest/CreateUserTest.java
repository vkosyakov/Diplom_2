package userTest;

import io.restassured.response.ValidatableResponse;
import org.example.rest.RestClient;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;


public class CreateUserTest {

    private UserClient userClient;
    private User user;
    private String token;

    @Before
    public void createTestData(){
        userClient = new UserClient();

    }

@After

    public void cleanUp(){
        if(token != null) {
           userClient.delete(token).statusCode(202);
        }
    }
    //Создание пользователя
   @Test
    public void usersCreated(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        response.assertThat().body("accessToken", notNullValue())
                .and().statusCode(200);

        token = response.extract().path("accessToken");

        StringBuilder sb = new StringBuilder(token);
        sb.delete(0,7);
        token = sb.toString();
    }

    //Создание уже зарегистрированного пользователя
    @Test
    public void usersCreatedWithAuth(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);

        token = response.extract().path("accessToken");
        StringBuilder sb = new StringBuilder(token);
        sb.delete(0,7);
        token = sb.toString();

        response = userClient.create(user);
        String message = response.extract().path("message");

        response.statusCode(403);
        Assert.assertEquals("User already exists", message);
    }

    //Создание пользователя без логина
    @Test
    public void usersCreatedWithNotEmail(){
        user = UserGenerator.withAllData();
        user.setEmail(null);
        ValidatableResponse response = userClient.create(user);
        String message = response.extract().path("message");

        response.statusCode(403);
        Assert.assertEquals("Email, password and name are required fields", message);

    }
    //создание пользователя без пароля
    @Test
    public void usersCreatedWithNotPassword(){
        user = UserGenerator.withAllData();
        user.setPassword(null);
        ValidatableResponse response = userClient.create(user);
        String message = response.extract().path("message");

        response.statusCode(403);
        Assert.assertEquals("Email, password and name are required fields", message);

    }

    //создание пользователя без имени
    @Test
    public void usersCreatedWithNotName(){
        user = UserGenerator.withAllData();
        user.setName(null);
        ValidatableResponse response = userClient.create(user);
        String message = response.extract().path("message");

        response.statusCode(403);
        Assert.assertEquals("Email, password and name are required fields", message);

    }



}
