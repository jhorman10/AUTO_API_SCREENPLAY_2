package com.automationexercise.tasks;

import com.automationexercise.models.UserData;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.rest.interactions.Put;

public class UpdateAccount implements Task {

    private final UserData userData;

    public UpdateAccount(UserData userData) {
        this.userData = userData;
    }

    public static UpdateAccount withData(UserData userData) {
        return instrumented(UpdateAccount.class, userData);
    }

    @Override
    @Step("{0} actualiza los datos de la cuenta")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/updateAccount")
                        .with(request -> {
                            request.contentType("multipart/form-data");
                            userData.toFormData().forEach((k, v) -> request.multiPart(k, v));
                            return request;
                        })
        );
    }
}
