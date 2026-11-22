package com.qa.portfolio.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public final class ScreenshotUtil {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private ScreenshotUtil() {
    }

    public static Path captureFullPage(WebDriver driver, String testName) throws IOException {
        if (!(driver instanceof ChromeDriver chromeDriver)) {
            throw new IllegalArgumentException("Full-page screenshots require a ChromeDriver instance");
        }

        Map<String, Object> metrics = chromeDriver.executeCdpCommand("Page.getLayoutMetrics", Map.of());
        Object contentSizeValue = metrics.get("contentSize");
        if (!(contentSizeValue instanceof Map<?, ?> contentSize)
                || !(contentSize.get("width") instanceof Number width)
                || !(contentSize.get("height") instanceof Number height)) {
            throw new IllegalStateException("Chrome did not return valid page dimensions for the screenshot");
        }

        Map<String, Object> clip = new HashMap<>();
        clip.put("x", 0);
        clip.put("y", 0);
        clip.put("width", width.doubleValue());
        clip.put("height", height.doubleValue());
        clip.put("scale", 1);

        Map<String, Object> screenshotOptions = new HashMap<>();
        screenshotOptions.put("format", "png");
        screenshotOptions.put("captureBeyondViewport", true);
        screenshotOptions.put("fromSurface", true);
        screenshotOptions.put("clip", clip);

        Map<String, Object> screenshot = chromeDriver.executeCdpCommand("Page.captureScreenshot", screenshotOptions);
        Object imageData = screenshot.get("data");
        if (!(imageData instanceof String base64Image)) {
            throw new IllegalStateException("Chrome did not return screenshot image data");
        }

        Path screenshotDirectory = Path.of("target", "screenshots");
        Files.createDirectories(screenshotDirectory);
        String safeTestName = testName.replaceAll("[^A-Za-z0-9._-]", "_");
        String fileName = safeTestName + "-" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
        Path screenshotPath = screenshotDirectory.resolve(fileName);
        Files.write(screenshotPath, Base64.getDecoder().decode(base64Image));
        return screenshotPath;
    }
}
