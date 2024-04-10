package org.example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class UserGenerator {
    @Step("Gegerate users for tests")
    public static User withAllData(){
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password(6,12);
        final String name = faker.name().firstName();
        return new User(email,password,name);
    }

}
