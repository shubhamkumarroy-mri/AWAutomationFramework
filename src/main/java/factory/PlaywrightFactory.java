package factory;

import com.microsoft.playwright.*;
import config.ConfigReader;

import utils.LoggerUtils;
import org.apache.logging.log4j.Logger;

public class PlaywrightFactory {

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static final Logger logger = LoggerUtils.getLogger(PlaywrightFactory.class);
    /**
     * Initializes Playwright and sets up the browser, context, and page.
     */
    public static Page initBrowser() {

        String browserName = ConfigReader.get("browser"); // e.g., "chrome", "firefox"
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        tlPlaywright.set(Playwright.create());

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

        switch (browserName.toLowerCase()) {
            case "firefox":
                tlBrowser.set(tlPlaywright.get().firefox().launch(options));
                break;
            case "webkit":
                tlBrowser.set(tlPlaywright.get().webkit().launch(options));
                break;
            case "chrome":
            case "chromium":
            default:
                tlBrowser.set(tlPlaywright.get().chromium().launch(options));
                break;
        }

        logger.info("Launching browser: " + browserName);


        tlContext.set(tlBrowser.get().newContext());
        tlPage.set(tlContext.get().newPage());

        return tlPage.get();
    }

    // Getters
    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getContext() {
        return tlContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    // Cleanup method
    public static void closeBrowser() {
        if (tlPage.get() != null)
            tlPage.get().close();
        if (tlContext.get() != null)
            tlContext.get().close();
        if (tlBrowser.get() != null)
            tlBrowser.get().close();
        if (tlPlaywright.get() != null)
            tlPlaywright.get().close();

        tlPage.remove();
        tlContext.remove();
        tlBrowser.remove();
        tlPlaywright.remove();
    }
}
