package hooks;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import factory.PlaywrightFactory;
import utils.LoggerUtils;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

import utils.ScreenshotUtils;

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
            
            String screenshotPath = ScreenshotUtils.takeScreenshot(page, scenario.getName());

            try {
                byte[] screenshot = Files.readAllBytes(Paths.get(screenshotPath));
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            } catch (java.io.IOException e) {
                logger.error("Failed to read screenshot file: " + screenshotPath, e);
            }

            logger.error("Scenario Failed: " + scenario.getName());
        
        } else {
            logger.info("Scenario Passed: " + scenario.getName());
        }

        PlaywrightFactory.closeBrowser();
        logger.info("=== Finished Scenario: " + scenario.getName() + " ===\n");
    }
}
