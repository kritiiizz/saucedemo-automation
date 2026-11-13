package com.kriti.automation.bdd;

import com.kriti.automation.driver.DriverFactory;
import com.kriti.automation.pages.CartPage;
import com.kriti.automation.pages.CheckoutInfoPage;
import com.kriti.automation.pages.InventoryPage;
import org.openqa.selenium.WebDriver;

/**
 * State shared between step classes for one scenario.
 * PicoContainer creates a new instance per scenario and injects it into every step class
 * that asks for it in its constructor, so scenarios never leak state into each other.
 */
public class ScenarioContext {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutInfoPage checkoutInfoPage;

    public WebDriver driver() {
        return DriverFactory.getDriver();
    }

    public InventoryPage inventoryPage() {
        if (inventoryPage == null) {
            inventoryPage = new InventoryPage(driver());
        }
        return inventoryPage;
    }

    public void setInventoryPage(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public CartPage cartPage() {
        if (cartPage == null) {
            throw new IllegalStateException("Cart page not opened in this scenario");
        }
        return cartPage;
    }

    public void setCartPage(CartPage cartPage) {
        this.cartPage = cartPage;
    }

    public CheckoutInfoPage checkoutInfoPage() {
        if (checkoutInfoPage == null) {
            throw new IllegalStateException("Checkout information page not reached in this scenario");
        }
        return checkoutInfoPage;
    }

    public void setCheckoutInfoPage(CheckoutInfoPage checkoutInfoPage) {
        this.checkoutInfoPage = checkoutInfoPage;
    }
}
