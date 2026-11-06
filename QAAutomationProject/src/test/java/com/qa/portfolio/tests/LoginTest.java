package com.qa.portfolio.tests;

import com.qa.portfolio.base.BaseTest;
import com.qa.portfolio.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(loginPage.isOnInventoryPage(), "Expected successful login to open inventory page");
    }

    @Test
    public void invalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginAs("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }

    @Test
    public void lockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginAs("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }
}
