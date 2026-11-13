package com.kriti.automation.pages;

import com.kriti.automation.config.ConfigReader;
import com.kriti.automation.pages.components.ErrorBanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final ErrorBanner errorBanner;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.errorBanner = new ErrorBanner(wait);
    }

    public LoginPage open() {
        log.info("Opening {}", ConfigReader.get("baseUrl"));
        driver.get(ConfigReader.get("baseUrl"));
        waitForVisible(loginButton);
        return this;
    }

    /** Submits the form without assuming success - use for both positive and negative tests. */
    public void login(String username, String password) {
        log.info("Logging in as '{}'", username);
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public InventoryPage loginAs(String username, String password) {
        login(username, password);
        return new InventoryPage(driver);
    }

    public InventoryPage loginAsStandardUser() {
        return loginAs(ConfigReader.get("standardUser"), ConfigReader.get("password"));
    }

    public String getErrorMessage() {
        return errorBanner.getText();
    }

    public boolean isLoaded() {
        return isDisplayed(loginButton);
    }
}
