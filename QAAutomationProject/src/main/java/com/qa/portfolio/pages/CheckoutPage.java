package com.qa.portfolio.pages;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.portfolio.utils.PageActions;

public class CheckoutPage {

    private static final Pattern PRICE_PATTERN = Pattern.compile("\\$([0-9]+(?:\\.[0-9]{1,2})?)");

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By firstNameInput = By.cssSelector("[data-test='firstName']");
    private final By lastNameInput = By.cssSelector("[data-test='lastName']");
    private final By postalCodeInput = By.cssSelector("[data-test='postalCode']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By cancelButton = By.cssSelector("[data-test='cancel']");
    private final By errorMessage = By.cssSelector("[data-test='error'], .error-message-container");
    private final By itemTotalLabel = By.cssSelector("[data-test='subtotal-label']");
    private final By taxLabel = By.cssSelector("[data-test='tax-label']");
    private final By totalLabel = By.cssSelector("[data-test='total-label']");
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterShippingInfo(String firstName, String lastName, String zipCode) {
        replaceText(firstNameInput, firstName);
        replaceText(lastNameInput, lastName);
        replaceText(postalCodeInput, zipCode);
    }

    public void clickContinue() {
        PageActions.clickAndWait(driver, continueButton, ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout-step-two.html"),
                ExpectedConditions.visibilityOfElementLocated(errorMessage)));
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public double getItemTotal() {
        return getPrice(itemTotalLabel);
    }

    public double getTax() {
        return getPrice(taxLabel);
    }

    public double getTotalPrice() {
        return getPrice(totalLabel);
    }

    public void clickFinish() {
        PageActions.clickAndWait(driver, finishButton, ExpectedConditions.urlContains("checkout-complete.html"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
    }

    public void clickCancel() {
        PageActions.clickAndWait(driver, cancelButton, ExpectedConditions.urlContains("cart.html"));
    }

    public String getCompleteHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }

    private void replaceText(By locator, String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
                "const input = arguments[0];"
                        + "const setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;"
                        + "setter.call(input, arguments[1]);"
                        + "input.dispatchEvent(new Event('input', { bubbles: true }));"
                        + "input.dispatchEvent(new Event('change', { bubbles: true }));",
                input, value);
        wait.until(driver -> value.equals(driver.findElement(locator).getDomProperty("value")));
    }

    private double getPrice(By locator) {
        String label = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        Matcher matcher = PRICE_PATTERN.matcher(label);
        if (!matcher.find()) {
            throw new IllegalStateException("Could not parse a price from checkout label: " + label);
        }
        return Double.parseDouble(matcher.group(1));
    }
}
