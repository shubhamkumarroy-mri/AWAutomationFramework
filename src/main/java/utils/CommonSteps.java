package utils;

import com.microsoft.playwright.Page;

import config.ConfigReader;
import factory.PlaywrightFactory;
import pages.LoginPage;

public class CommonSteps {

    public static void login(String username, String password) {
        Page page = PlaywrightFactory.getPage();
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage(ConfigReader.get("url"));
        loginPage.enterUsername(ConfigReader.get("username"));
        loginPage.enterPassword(ConfigReader.get("password"));
        loginPage.clickLogin();
        loginPage.selectProfileAndClient();
        loginPage.isDashboardVisible();
    }
}
