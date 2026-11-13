package com.kriti.automation.bdd;

import com.kriti.automation.config.ConfigReader;
import com.kriti.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private final ScenarioContext context;

    public LoginSteps(ScenarioContext context) {
        this.context = context;
    }

    private LoginPage loginPage() {
        return new LoginPage(context.driver());
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage().open();
    }

    @Given("I am logged in as {string}")
    public void iAmLoggedInAs(String username) {
        context.setInventoryPage(loginPage().open().loginAs(username, ConfigReader.get("password")));
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWith(String username, String password) {
        loginPage().login(username, password);
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        Assert.assertTrue(context.inventoryPage().isLoaded());
    }

    @Then("I should see a login error containing {string}")
    public void iShouldSeeALoginError(String expected) {
        String actual = loginPage().getErrorMessage();
        Assert.assertTrue(actual.contains(expected), "Got: " + actual);
    }
}
