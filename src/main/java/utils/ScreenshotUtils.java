package utils;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    public static String takeScreenshot(Page page, String testName) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String screenshotDir = "target/screenshots/";
            Files.createDirectories(Paths.get(screenshotDir));

            String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            return screenshotPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}