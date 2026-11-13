package com.kriti.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By header = By.className("complete-header");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        waitForVisible(header);
    }

    public String getHeader() {
        return getText(header);
    }

    public InventoryPage backHome() {
        click(backHomeButton);
        return new InventoryPage(driver);
    }
}
