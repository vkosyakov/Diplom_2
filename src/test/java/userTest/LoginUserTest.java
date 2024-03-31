package userTest;

import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserCredentials;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest {
    private UserClient userClient;
    private User user;
    private String refreshToken;
    private String accessToken;


    @Before
    public void createTestData(){
        userClient = new UserClient();
    }
    @After
    public void tearDown(){
        if(refreshToken != null)
            userClient.logout(refreshToken);
        if (accessToken != null)
            userClient.delete(accessToken);
    }

    //логин под авторизованным пользователем
    @Test
    public void loginWithAuto(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);

        accessToken = response.extract().path("accessToken");
        StringBuilder sb = new StringBuilder(accessToken);
        sb.delete(0,7);
        accessToken = sb.toString();

        response = userClient.login(UserCredentials.from(user));

        refreshToken = response.extract().path("refreshToken");

        response.assertThat().body("refreshToken", notNullValue())
                .and().statusCode(200);

    }

    // авторизация с неверными логином и паролем
        @Test
        public void loginWithOutAuto(){
            user = UserGenerator.withAllData();
            ValidatableResponse response = userClient.login(UserCredentials.from(user));
            String message = response.extract().path("message");

            response.statusCode(401);
            Assert.assertEquals(message,"email or password are incorrect");

    }

}
