package com.qa.portfolio.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By cartItems = By.cssSelector(".cart_item");
    private final By itemTitle = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrice = By.cssSelector("[data-test='inventory-item-price']");
    private final By continueShoppingButton = By.cssSelector("[data-test='continue-shopping']");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }

    public int getCartItemCount() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItems));
        return driver.findElements(cartItems).size();
    }

    public void removeItemByName(String productName) {
        WebElement item = findItemByName(productName);
        WebElement removeButton = item.findElement(By.xpath(".//button[starts-with(@data-test, 'remove-')]"));
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
        wait.until(ExpectedConditions.stalenessOf(item));
    }

    public double getItemPriceByName(String productName) {
        WebElement item = findItemByName(productName);
        String price = item.findElement(itemPrice).getText().replace("$", "").trim();
        return Double.parseDouble(price);
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public void clickCheckout() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        button.click();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlContains("checkout-step-one.html"));
        } catch (TimeoutException ignored) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                    driver.findElement(checkoutButton));
            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        }
    }

    private WebElement findItemByName(String productName) {
        List<WebElement> items = driver.findElements(cartItems);
        for (WebElement item : items) {
            if (item.findElement(itemTitle).getText().equals(productName)) {
                return item;
            }
        }
        throw new NoSuchElementException("Cart item not found: " + productName);
    }
}
