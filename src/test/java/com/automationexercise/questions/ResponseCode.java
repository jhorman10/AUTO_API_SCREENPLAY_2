package com.automationexercise.questions;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ResponseCode implements Question<Integer> {

    public static ResponseCode value() {
        return new ResponseCode();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        Response response = LastResponse.received().answeredBy(actor);
        try {
            Integer rc = response.jsonPath().getInt("responseCode");
            if (rc != null) {
                return rc;
            }
        } catch (Exception ignored) {
        }
        try {
            return response.getStatusCode();
        } catch (Exception e) {
            return null;
        }
    }
}
