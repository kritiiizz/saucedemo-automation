package com.kriti.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By itemNames = By.className("inventory_item_name");

    public CartPage(WebDriver driver) {
        super(driver);
        waitForVisible(checkoutButton);
    }

    private By removeButton(String productName) {
        return byDataTest("remove-" + toSlug(productName));
    }

    public List<String> getItemNames() {
        return getTexts(itemNames);
    }

    /** Removes the product and waits until its row has gone. */
    public CartPage removeItem(String productName) {
        log.info("Remove from cart page: {}", productName);
        By button = removeButton(productName);
        click(button);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(button));
        return this;
    }

    public CheckoutInfoPage checkout() {
        click(checkoutButton);
        return new CheckoutInfoPage(driver);
    }

    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        return new InventoryPage(driver);
    }
}
