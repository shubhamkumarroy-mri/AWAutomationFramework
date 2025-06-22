package test.java.stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.*;
import main.java.config.ConfigReader;
import main.java.factory.PlaywrightFactory;
import org.testng.Assert;
import org.xml.sax.Locator;

public class LoginSteps {

    private Page page;

    String url = ConfigReader.get("url");

    @Given("user is on the login page")
    public void userIsOnLoginPage() {
        page = PlaywrightFactory.getPage();
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL is not configured in the properties file");
        }
        page.navigate(url);
    }

    @When("user logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        page.fill("#username", username);
        page.fill("#password", password);
        page.click("#loginbtn-164a-15a5");
    }

    @And("user selects profile and client")
    public void userSelectsProfileAndClient() {
        // Select profile and client if necessary
        // We can use getProperty method to get the profile and client from the properties file, THOUGH not implemented here

        page.click("selectclientbtn-164a-15a5");
    }

    @Then("user should be redirected to the dashboard")
    public void userShouldBeRedirectedToDashboard() {
        // Check for the text "Dashboard"
        Locator heading = page.locator("div.alert h5");
        Assert.assertTrue(heading.textContent().contains("Dashboard"), "Dashboard heading is not visible");
    }

}
