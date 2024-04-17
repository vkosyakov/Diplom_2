package userTest;

import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateUserTest {
    private UserClient userClient;
    private User user;
    private String token;
    @Before
    public void createTestData(){
        userClient = new UserClient();

    }
    @After
    public void cleanUp() {
        if (token != null)
            userClient.delete(token);
    }

    //измнение данных без авторизацией
    @Test
    public void updateEmailAndPasswordWithOutAuth(){
        Faker faker = new Faker();
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        response.statusCode(200);

        token = "";
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        response = userClient.update(token,user);

        String message = response.extract().path("message");
        response.statusCode(401);
        Assert.assertEquals(message,"You should be authorised");
    }

    //измнение пароля с авторизацией
    @Test
    public void updatePasswordWithAuth(){
        Faker faker = new Faker();
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        response.statusCode(200);

        token = response.extract().path("accessToken");
        StringBuilder sb = new StringBuilder(token);
        sb.delete(0,7);
        token = sb.toString();

        user.setPassword(faker.internet().password(6,9));

        userClient.update(token,user);
        response.statusCode(200);
    }

    //измнение имени с авторизацией
    @Test
    public void updateNameWithAuth(){
        Faker faker = new Faker();
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        response.statusCode(200);

        token = response.extract().path("accessToken");
        StringBuilder sb = new StringBuilder(token);
        sb.delete(0,7);
        token = sb.toString();

        user.setName(faker.name().firstName());

        userClient.update(token,user);
        response.statusCode(200);
    }

    //изменение email с авторизацией
    @Test
    public void updateEmailWithAuth(){
        Faker faker = new Faker();
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);
        response.statusCode(200);

        token = response.extract().path("accessToken");
        StringBuilder sb = new StringBuilder(token);
        sb.delete(0,7);
        token = sb.toString();

        user.setEmail(faker.internet().emailAddress());

        userClient.update(token,user);
        response.statusCode(200);
    }



}
