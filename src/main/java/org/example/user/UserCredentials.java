package org.example.user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.rest.RestClient;

@Getter
@Setter
@AllArgsConstructor
public class UserCredentials {
private String email;
private String password;

@Step("Get email and password for login")
public static UserCredentials from (User user){
    return new UserCredentials(user.getEmail(), user.getPassword());
}

}
