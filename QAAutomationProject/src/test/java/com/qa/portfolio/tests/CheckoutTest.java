package com.qa.portfolio.tests;

import com.qa.portfolio.base.BaseTest;
import com.qa.portfolio.pages.CartPage;
import com.qa.portfolio.pages.CheckoutPage;
import com.qa.portfolio.pages.InventoryPage;
import com.qa.portfolio.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private static final String BACKPACK = "Sauce Labs Backpack";

    @Test
    public void successfulCheckout() {
        LoginPage loginPage = loginAsStandardUser();
        Assert.assertTrue(loginPage.isOnInventoryPage(), "Login should navigate to inventory");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain the backpack");
        double cartItemPrice = cartPage.getItemPriceByName(BACKPACK);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterShippingInfo("Taylor", "Tester", "12345");
        checkoutPage.clickContinue();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Valid shipping information should navigate to checkout step two");

        double itemTotal = checkoutPage.getItemTotal();
        double tax = checkoutPage.getTax();
        double total = checkoutPage.getTotalPrice();
        Assert.assertEquals(itemTotal, cartItemPrice, 0.001, "Summary subtotal should match the cart");
        Assert.assertEquals(tax, Math.round(itemTotal * 0.08 * 100.0) / 100.0, 0.001,
                "Tax should be 8% of the item subtotal, rounded to cents");
        Assert.assertEquals(total, itemTotal + tax, 0.001, "Total should equal subtotal plus tax");

        checkoutPage.clickFinish();
        Assert.assertEquals(checkoutPage.getCompleteHeaderText(), "Thank you for your order!");
    }

    @Test
    public void checkoutRequiresPostalCode() {
        LoginPage loginPage = loginAsStandardUser();
        Assert.assertTrue(loginPage.isOnInventoryPage(), "Login should navigate to inventory");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();
        new CartPage(driver).clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterShippingInfo("Taylor", "Tester", "");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"),
                "Checkout should explain that a postal code is required");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),
                "Invalid shipping information should keep the user on step one");
    }

    @Test
    public void cancelCheckoutReturnsToCart() {
        LoginPage loginPage = loginAsStandardUser();
        Assert.assertTrue(loginPage.isOnInventoryPage(), "Login should navigate to inventory");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();
        new CartPage(driver).clickCheckout();

        new CheckoutPage(driver).clickCancel();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"),
                "Canceling checkout should return to cart without completing the order");
        Assert.assertTrue(new InventoryPage(driver).isCartBadgeDisplayed(),
                "Canceling checkout should preserve the cart contents");
    }

    private LoginPage loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");
        return loginPage;
    }
}
