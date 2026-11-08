package com.qa.portfolio.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By addBackpackButton = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private final By removeBackpackButton = By.cssSelector("[data-test='remove-sauce-labs-backpack']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public void addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addBackpackButton)).click();
    }

    public void removeBackpackFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeBackpackButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    public String getCartBadgeCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public boolean isCartBadgeDisplayed() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }
}
