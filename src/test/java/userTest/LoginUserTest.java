package userTest;

import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.example.UserGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest {
    private UserClient userClient;
    private User user;
    private String refreshTocken;
    private String accessTocken;


    @Before
    public void createTestData(){
        userClient = new UserClient();
    }
    @After
    public void tearDown(){
        if(refreshTocken!= null)
            userClient.logout(refreshTocken);
        if (accessTocken != null)
            userClient.delete(accessTocken);
    }

    //логин под авторизованным пользователем
    @Test
    public void loginWithAuto(){
        user = UserGenerator.withAllData();
        ValidatableResponse response = userClient.create(user);

        accessTocken = response.extract().path("accessTocken");

        response = userClient.login(UserCredentials.from(user));

        refreshTocken = response.extract().path("refreshToken");

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
