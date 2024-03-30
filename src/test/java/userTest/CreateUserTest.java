package userTest;

import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;


public class CreateUserTest {

    private UserClient userClient;
    private User user;
    private String tocken;

    @Before
    public void createTestData(){
        userClient = new UserClient();

    }

@After

    public void cleanUp(){
        if(tocken!= null)
        userClient.delete(tocken);
    }
    //Создание пользователя
   @Test
    public void usersCreated(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        response.assertThat().body("accessToken", notNullValue())
                .and().statusCode(200);

        tocken = response.extract().path("accessToken");
    }

    //Создание уже зарегистрированного пользователя
    @Test
    public void usersCreatedWithAuto(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        tocken = response.extract().path("accessToken");

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
