package com.qa.portfolio.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class PageActions {

    private static final int MAX_CLICK_ATTEMPTS = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final Duration RETRY_TIMEOUT = Duration.ofSeconds(2);

    private PageActions() {
    }

    public static void clickAndWait(WebDriver driver, By locator, ExpectedCondition<?> expectedEffect) {
        WebDriverException lastFailure = null;
        for (int attempt = 1; attempt <= MAX_CLICK_ATTEMPTS; attempt++) {
            try {
                new WebDriverWait(driver, TIMEOUT).until(
                        currentDriver -> "complete".equals(((JavascriptExecutor) currentDriver)
                                .executeScript("return document.readyState")));
                WebElement element = new WebDriverWait(driver, TIMEOUT)
                        .until(ExpectedConditions.elementToBeClickable(locator));
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", element);
                element = new WebDriverWait(driver, TIMEOUT)
                        .until(ExpectedConditions.elementToBeClickable(locator));

                if (attempt == MAX_CLICK_ATTEMPTS) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                } else {
                    element.click();
                }

                Duration effectTimeout = attempt == MAX_CLICK_ATTEMPTS ? TIMEOUT : RETRY_TIMEOUT;
                new WebDriverWait(driver, effectTimeout).until(expectedEffect);
                return;
            } catch (WebDriverException failure) {
                lastFailure = failure;
                if (attempt == MAX_CLICK_ATTEMPTS) {
                    throw failure;
                }
            }
        }
        throw lastFailure;
    }
}
