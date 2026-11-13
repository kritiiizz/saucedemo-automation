package com.kriti.automation.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** The red error box SauceDemo shows on the login and checkout forms. */
public class ErrorBanner {

    private static final By MESSAGE = By.cssSelector("[data-test='error']");

    private final WebDriverWait wait;

    public ErrorBanner(WebDriverWait wait) {
        this.wait = wait;
    }

    public String getText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText().trim();
    }
}
