package com.kriti.automation.tests;

import com.kriti.automation.base.BaseTest;
import com.kriti.automation.pages.InventoryPage;
import com.kriti.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "TC_LOGIN_01: Valid user can log in")
    public void validUserCanLogin() {
        InventoryPage inventory = openLoginPage().loginAsStandardUser();
        Assert.assertTrue(inventory.isLoaded(), "Products page should be shown after a valid login");
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                // username,        password,       expected error (partial)
                {"standard_user",   "wrong_pass",   "Username and password do not match"},  // TC_LOGIN_02
                {"locked_out_user", "secret_sauce", "this user has been locked out"},       // TC_LOGIN_03
                {"",                "secret_sauce", "Username is required"},                // TC_LOGIN_04
                {"standard_user",   "",             "Password is required"},                // TC_LOGIN_05
        };
    }

    @Test(dataProvider = "invalidLogins", groups = {"regression"},
            description = "TC_LOGIN_02-05: Invalid logins are rejected with the right message")
    public void invalidLoginShowsError(String username, String password, String expectedError) {
        LoginPage login = openLoginPage();
        login.login(username, password);

        String actual = login.getErrorMessage();
        Assert.assertTrue(actual.contains(expectedError),
                "Expected error containing '" + expectedError + "' but got '" + actual + "'");
        Assert.assertTrue(login.isLoaded(), "User should stay on the login page");
    }

    @Test(groups = {"regression"}, description = "TC_LOGIN_06: User can log out")
    public void userCanLogout() {
        LoginPage login = openLoginPage().loginAsStandardUser().logout();
        Assert.assertTrue(login.isLoaded(), "Login page should be shown after logout");
    }
}
