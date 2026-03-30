package com.automationexercise.stepdefinitions;

import static org.hamcrest.Matchers.equalTo;

import com.automationexercise.models.UserData;
import com.automationexercise.questions.ResponseCode;
import com.automationexercise.questions.ResponseMessage;
import com.automationexercise.questions.UserDetailEmail;
import com.automationexercise.tasks.CreateAccount;
import com.automationexercise.tasks.DeleteAccount;
import com.automationexercise.tasks.GetUserDetail;
import com.automationexercise.tasks.UpdateAccount;
import com.automationexercise.utils.Constants;
import com.automationexercise.utils.TestDataGenerator;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ApiCrudStepDefinitions {

    private Actor actor;
    private UserData userData;

    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
        actor = Actor.named("QA Tester");
        String baseUrl = TestDataGenerator.baseUrl();
        actor.whoCan(CallAnApi.at(baseUrl));
    }

    @After
    public void tearDown() {
    }

    @Given("el usuario prepara los datos de registro con un email unico")
    public void prepareRegistrationData() {
        userData = TestDataGenerator.validUser();
    }

    @Given("existe una cuenta de usuario registrada")
    public void ensureAccountExists() {
        if (userData == null) {
            userData = TestDataGenerator.validUser();
            actor.attemptsTo(CreateAccount.withData(userData));
        }
    }

    @When("el usuario envia la solicitud de creacion de cuenta")
    public void sendCreateAccountRequest() {
        actor.attemptsTo(CreateAccount.withData(userData));
    }

    @When("el usuario consulta los detalles de la cuenta por email")
    public void queryAccountDetailsByEmail() {
        actor.attemptsTo(GetUserDetail.byEmail(userData.getEmail()));
    }

    @When("el usuario actualiza el nombre de la cuenta")
    public void updateAccountName() {
        userData.setName("Updated QA " + System.currentTimeMillis());
        userData.setFirstname("Updated");
        actor.attemptsTo(UpdateAccount.withData(userData));
    }

    @When("el usuario envia la solicitud de eliminacion de cuenta")
    public void sendDeleteAccountRequest() {
        actor.attemptsTo(
                DeleteAccount.withCredentials(userData.getEmail(), userData.getPassword())
        );
    }

    @Then("el codigo de respuesta es {int}")
    public void verifyResponseCode(int expectedCode) {
        try {
            Response resp = LastResponse.received().answeredBy(actor);
            System.out.println("[DEBUG] LastResponse HTTP status=" + resp.getStatusCode());
            System.out.println("[DEBUG] LastResponse body=" + resp.asString());
        } catch (Exception ignored) {
        }

        actor.should(seeThat(ResponseCode.value(), equalTo(expectedCode)));
    }

    @And("el mensaje de respuesta es {string}")
    public void verifyResponseMessage(String expectedMessage) {
        String expected = Constants.Messages.forKey(expectedMessage);
        actor.should(seeThat(ResponseMessage.value(), equalTo(expected)));
    }

    @And("los detalles contienen el email registrado")
    public void verifyDetailsContainEmail() {
        actor.should(
                seeThat(UserDetailEmail.value(), equalTo(userData.getEmail()))
        );
    }
}
