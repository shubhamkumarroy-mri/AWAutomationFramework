package test.java.hooks;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import main.java.factory.PlaywrightFactory;
import org.apache.commons.io.FileUtils;
import main.java.utils.LoggerUtils;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Hooks {

    private Page page;
    private static final Logger logger = LoggerUtils.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        page = PlaywrightFactory.initBrowser();
        logger.info("=== Starting Scenario: " + scenario.getName() + " ===");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // Capture screenshot
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setPath(
                    Paths.get("screenshots", scenario.getName().replaceAll("[^a-zA-Z0-9]", "_") + ".png")
            ).setFullPage(true));
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
            logger.error("❌ Scenario Failed: " + scenario.getName());
        } else {
            logger.info("✅ Scenario Passed: " + scenario.getName());
        }

        PlaywrightFactory.closeBrowser();
        logger.info("=== Finished Scenario: " + scenario.getName() + " ===\n");
    }
}
