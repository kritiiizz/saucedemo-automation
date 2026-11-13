package com.kriti.automation.tests;

import com.kriti.automation.base.BaseTest;
import com.kriti.automation.config.ConfigReader;
import com.kriti.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

public class AccessControlTest extends BaseTest {

    @DataProvider(name = "protectedPages")
    public Object[][] protectedPages() {
        return new Object[][]{
                {"inventory.html"},          // TC_SEC_01
                {"cart.html"},               // TC_SEC_02
                {"checkout-step-one.html"},  // TC_SEC_03
        };
    }

    @Test(dataProvider = "protectedPages", groups = {"regression"},
            description = "TC_SEC_01-03: Protected pages redirect to login when not logged in")
    public void protectedPageRequiresLogin(String path) {
        String url = URI.create(ConfigReader.get("baseUrl")).resolve(path).toString();
        driver().get(url);

        LoginPage login = new LoginPage(driver());
        Assert.assertTrue(login.isLoaded(), "Login page should be shown instead of " + path);

        String error = login.getErrorMessage();
        Assert.assertTrue(error.contains("when you are logged in"),
                "Expected a 'must be logged in' error but got '" + error + "'");
    }
}
