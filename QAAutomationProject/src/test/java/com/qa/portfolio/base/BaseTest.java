package com.qa.portfolio.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

import com.qa.portfolio.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(groups = "ui")
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        options.setCapability("goog:loggingPrefs", loggingPreferences);
        boolean headless = Boolean.getBoolean("headless");
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod(alwaysRun = true, groups = "ui")
    public void tearDown(ITestResult testResult) {
        if (driver != null) {
            try {
                if (testResult.getStatus() == ITestResult.FAILURE) {
                    System.err.println("Test failed at URL " + driver.getCurrentUrl()
                            + " with window size " + driver.manage().window().getSize());
                    String testName = testResult.getTestClass().getName() + "." + testResult.getMethod().getMethodName();
                    Path screenshotPath = ScreenshotUtil.captureFullPage(driver, testName);
                    System.err.println("Failure screenshot saved to " + screenshotPath.toAbsolutePath());
                    Path basePath = screenshotPath.resolveSibling(
                            screenshotPath.getFileName().toString().replaceFirst("\\.png$", ""));
                    Files.writeString(Path.of(basePath.toString() + ".html"), driver.getPageSource());
                    String pageState = "URL: " + driver.getCurrentUrl()
                            + System.lineSeparator() + "Window size: " + driver.manage().window().getSize()
                            + System.lineSeparator() + "document.readyState: "
                            + ((org.openqa.selenium.JavascriptExecutor) driver)
                                    .executeScript("return document.readyState");
                    Files.writeString(Path.of(basePath.toString() + ".txt"), pageState + System.lineSeparator());
                    StringBuilder browserLogs = new StringBuilder();
                    for (LogEntry entry : driver.manage().logs().get(LogType.BROWSER)) {
                        browserLogs.append(entry.getLevel()).append(": ")
                                .append(entry.getMessage()).append(System.lineSeparator());
                    }
                    Files.writeString(Path.of(basePath.toString() + ".browser.log"), browserLogs.toString());
                }
            } catch (IOException | RuntimeException screenshotFailure) {
                Throwable testFailure = testResult.getThrowable();
                if (testFailure != null) {
                    testFailure.addSuppressed(screenshotFailure);
                    System.err.println("Could not capture failure screenshot: " + screenshotFailure.getMessage());
                } else {
                    throw new RuntimeException("Could not capture failure screenshot", screenshotFailure);
                }
            } finally {
                driver.quit();
            }
        }
    }
}
