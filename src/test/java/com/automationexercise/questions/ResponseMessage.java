package com.automationexercise.questions;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ResponseMessage implements Question<String> {

    public static ResponseMessage value() {
        return new ResponseMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        Response response = LastResponse.received().answeredBy(actor);
        try {
            return response.jsonPath().getString("message");
        } catch (Exception e) {
            return null;
        }
    }
}
