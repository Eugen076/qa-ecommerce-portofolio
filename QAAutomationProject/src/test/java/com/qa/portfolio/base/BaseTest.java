package com.qa.portfolio.base;

import java.io.IOException;
import java.nio.file.Path;

import com.qa.portfolio.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        boolean headless = Boolean.getBoolean("headless");
        if (headless) {
            options.addArguments("--headless=new", "--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        if (!headless) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult) {
        if (driver != null) {
            try {
                if (testResult.getStatus() == ITestResult.FAILURE) {
                    System.err.println("Test failed at URL " + driver.getCurrentUrl()
                            + " with window size " + driver.manage().window().getSize());
                    String testName = testResult.getTestClass().getName() + "." + testResult.getMethod().getMethodName();
                    Path screenshotPath = ScreenshotUtil.captureFullPage(driver, testName);
                    System.err.println("Failure screenshot saved to " + screenshotPath.toAbsolutePath());
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
