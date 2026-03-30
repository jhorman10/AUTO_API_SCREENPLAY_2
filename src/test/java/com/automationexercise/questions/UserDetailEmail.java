package com.automationexercise.questions;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class UserDetailEmail implements Question<String> {

    public static UserDetailEmail value() {
        return new UserDetailEmail();
    }

    @Override
    public String answeredBy(Actor actor) {
        Response response = LastResponse.received().answeredBy(actor);
        try {
            return response.jsonPath().getString("user.email");
        } catch (Exception e) {
            return null;
        }
    }
}
