package com.kriti.automation.pages;

import com.kriti.automation.pages.components.ErrorBanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Checkout step one: customer information. */
public class CheckoutInfoPage extends BasePage {

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final ErrorBanner errorBanner;

    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
        this.errorBanner = new ErrorBanner(wait);
        waitForVisible(firstNameField);
    }

    public CheckoutInfoPage enterDetails(String firstName, String lastName, String postalCode) {
        log.info("Customer details: '{}' / '{}' / '{}'", firstName, lastName, postalCode);
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        return this;
    }

    /** Clicks Continue without assuming the form was valid. */
    public void submit() {
        click(continueButton);
    }

    public CheckoutOverviewPage continueToOverview() {
        submit();
        return new CheckoutOverviewPage(driver);
    }

    public String getErrorMessage() {
        return errorBanner.getText();
    }
}
