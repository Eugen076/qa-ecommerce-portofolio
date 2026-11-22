package com.qa.portfolio.tests;

import com.qa.portfolio.base.BaseTest;
import com.qa.portfolio.pages.InventoryPage;
import com.qa.portfolio.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    @BeforeMethod(groups = "ui")
    public void loginToInventory() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @Test(groups = "ui")
    public void verifyInventoryPageTitle() {
        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");
    }

    @Test(groups = "ui")
    public void addSingleItemToCart() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");
    }

    @Test(groups = "ui")
    public void removeItemFromCart() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        inventoryPage.removeBackpackFromCart();

        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed());
    }
}
