package com.automationexercise.tasks;

import com.automationexercise.utils.Constants;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetUserDetail implements Task {

    private final String email;

    public GetUserDetail(String email) {
        this.email = email;
    }

    public static GetUserDetail byEmail(String email) {
        return instrumented(GetUserDetail.class, email);
    }

    @Override
    @Step("{0} consulta los detalles de la cuenta por email")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Get.resource(Constants.Endpoints.GET_USER_DETAIL_BY_EMAIL)
                .with(request -> request.queryParam(Constants.FormFields.EMAIL, email))
        );
    }
}
