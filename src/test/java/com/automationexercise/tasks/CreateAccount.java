package com.automationexercise.tasks;

import com.automationexercise.models.UserData;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class CreateAccount implements Task {

    private final UserData userData;

    public CreateAccount(UserData userData) {
        this.userData = userData;
    }

    public static CreateAccount withData(UserData userData) {
        return instrumented(CreateAccount.class, userData);
    }

    @Override
    @Step("{0} crea una cuenta de usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/createAccount")
                        .with(request -> {
                            request.contentType("multipart/form-data");
                            userData.toFormData().forEach((k, v) -> request.multiPart(k, v));
                            return request;
                        })
        );
    }
}
