package stepdefinitions;

import com.microsoft.playwright.Page;

import factory.PlaywrightFactory;
import io.cucumber.java.en.*;
import config.ConfigReader;
import pages.LoginPage;
import org.testng.Assert;

public class LoginSteps {

    private Page page;
    private LoginPage loginPage;

    @Given("user is on the login page")
    public void userIsOnLoginPage() {
        page = PlaywrightFactory.getPage();
        loginPage = new LoginPage(page);
        String url = ConfigReader.get("url");

        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL is not configured in the properties file");
        }

        loginPage.navigateToLoginPage(url);
    }

    @When("user logs in with username {string} and password {string}")
    public void userLogsInWithUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @And("user selects profile and client")
    public void userSelectsProfileAndClient() {
        loginPage.selectProfileAndClient();
    }

    @Then("user should be redirected to the dashboard")
    public void userShouldBeRedirectedToDashboard() {
        Assert.assertTrue(loginPage.isDashboardVisible(), "Dashboard heading is not visible");
    }
}
