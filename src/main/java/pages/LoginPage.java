package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private Page page;

    // Constructor to initialize the page object
    public LoginPage(Page page) {
        this.page = page;
    }

    // Locators used in the login page
    private final String usernameField = "#username";
    private final String passwordField = "#password";
    private final String loginButton = ".btn.btn-primary span";
    private final String selectProfileBtn = ".form-group.required";
    private final String dashboardHeading = "div.alert h5";


    // Methods to interact with the login page
    public void navigateToLoginPage(String url) {
        page.navigate(url);
    }

    public void enterUsername(String username) {
        page.fill(usernameField, username);
    }

    public void enterPassword(String password) {
        page.fill(passwordField, password);
    }

    public void clickLogin() {
        page.click(loginButton);
    }

    public void selectProfileAndClient() {
        // We can use getProperty method to get the profile and client from the
        // properties file, THOUGH not implemented here
        // For now, we will simply click Continue
        page.click(selectProfileBtn);
    }

    public boolean isDashboardVisible() {
        return page.locator(dashboardHeading).textContent().contains("Dashboard");
    }
}
