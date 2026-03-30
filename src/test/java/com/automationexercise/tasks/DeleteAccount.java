package com.automationexercise.tasks;

import com.automationexercise.utils.Constants;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.rest.interactions.Delete;

public class DeleteAccount implements Task {

    private final String email;
    private final String password;

    public DeleteAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static DeleteAccount withCredentials(String email, String password) {
        return instrumented(DeleteAccount.class, email, password);
    }

    @Override
    @Step("{0} elimina la cuenta de usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Delete.from(Constants.Endpoints.DELETE_ACCOUNT)
                .with(request -> {
                    request.contentType("multipart/form-data");
                    request.multiPart(Constants.FormFields.EMAIL, email);
                    request.multiPart(Constants.FormFields.PASSWORD, password);
                    return request;
                })
        );
    }
}
