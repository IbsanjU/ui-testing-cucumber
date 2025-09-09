package step_definitions;

import io.cucumber.java.en.*;
import pages.LoginPage;
import utils.DriverManager;

import static org.junit.Assert.*;

public class LoginSteps {
    private LoginPage loginPage;

    public LoginSteps() {
        loginPage = new LoginPage(DriverManager.getDriver());
    }

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        loginPage.navigateToLoginPage();
    }

    @When("the user enters username {string}")
    public void userEntersUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("the user enters password {string}")
    public void userEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the user clicks the login button")
    public void userClicksLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("the user should be redirected to the dashboard")
    public void userShouldBeRedirectedToDashboard() {
        assertTrue("Expected user to be redirected to dashboard after successful login, " +
                        "but current URL is: " + DriverManager.getDriver().getCurrentUrl(),
                loginPage.isOnDashboard());
    }

    @Then("the welcome message should display {string}")
    public void welcomeMessageShouldDisplay(String expectedMessage) {
        assertEquals("Welcome message mismatch. Expected: '" + expectedMessage +
                        "' but got: '" + loginPage.getWelcomeMessage() + "'",
                expectedMessage, loginPage.getWelcomeMessage());
    }

    @Then("an error message {string} should be displayed")
    public void errorMessageShouldBeDisplayed(String expectedError) {
        assertTrue("Error message should be displayed but it's not visible",
                loginPage.isErrorMessageDisplayed());

        assertEquals("Error message mismatch. Expected: '" + expectedError +
                        "' but got: '" + loginPage.getErrorMessage() + "'",
                expectedError, loginPage.getErrorMessage());
    }

    @Then("the user should remain on the login page")
    public void userShouldRemainOnLoginPage() {
        assertTrue("User should still be on login page but current URL is: " +
                        DriverManager.getDriver().getCurrentUrl(),
                loginPage.isOnLoginPage());
    }
}