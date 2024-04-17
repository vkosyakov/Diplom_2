package org.example.order;

import io.qameta.allure.Step;
import org.example.order.Order;

public class OrderGenerator {
    @Step("Generate order with all the data")
    public static Order withIngredients() {
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};
        return new Order(ingredients);
    }
    @Step("Generate order with invalide data")
    public static Order invalidHash(){
        final String[] ingredients = {"Y1c0c5a71d1f82001bdaaa6d", "Y1c0c5a71d1f82001bdaaa6f"};
                return new Order(ingredients);
    }
}
