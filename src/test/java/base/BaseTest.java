package test.java.base;

import com.microsoft.playwright.Page;
import factory.PlaywrightFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;
import utils.LoggerUtils;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected Page page;
    private PlaywrightFactory playwrightFactory;
    protected static Logger logger;

    @BeforeClass
    public void setUp() {
        logger = LoggerUtils.getLogger(BaseTest.class);
        logger.info("===== Test Setup Started =====");

        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.initBrowser(ConfigReader.get("browser"));

        logger.info("Browser launched and Page instance created.");
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            page.context().browser().close();
            logger.info("Browser closed.");
        }
        logger.info("===== Test Teardown Completed =====");
    }
}
