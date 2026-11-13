package com.kriti.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class InventoryPage extends BasePage {

    public enum SortOption {
        NAME_A_TO_Z("az", "Name (A to Z)"),
        NAME_Z_TO_A("za", "Name (Z to A)"),
        PRICE_LOW_TO_HIGH("lohi", "Price (low to high)"),
        PRICE_HIGH_TO_LOW("hilo", "Price (high to low)");

        private final String value;
        private final String label;

        SortOption(String value, String label) {
            this.value = value;
            this.label = label;
        }

        /** Finds an option by the text shown in the dropdown, e.g. "Price (low to high)". */
        public static SortOption fromLabel(String label) {
            return Arrays.stream(values())
                    .filter(o -> o.label.equalsIgnoreCase(label.trim()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown sort option: " + label));
        }
    }

    private final By pageTitle = By.className("title");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By itemImages = By.cssSelector(".inventory_item img");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By sortDropdown = By.className("product_sort_container");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    private By addButton(String productName) {
        return byDataTest("add-to-cart-" + toSlug(productName));
    }

    private By removeButton(String productName) {
        return byDataTest("remove-" + toSlug(productName));
    }

    public boolean isLoaded() {
        return isDisplayed(pageTitle) && "Products".equals(getText(pageTitle));
    }

    /** Clicks "Add to cart" and waits until the button has turned into "Remove". */
    public InventoryPage addToCart(String productName) {
        log.info("Add to cart: {}", productName);
        click(addButton(productName));
        waitForVisible(removeButton(productName));
        return this;
    }

    /** Clicks "Remove" and waits until the button has turned back into "Add to cart". */
    public InventoryPage removeFromCart(String productName) {
        log.info("Remove from cart: {}", productName);
        click(removeButton(productName));
        waitForVisible(addButton(productName));
        return this;
    }

    /** Text of the product's cart button, whichever state it is in ("Add to cart" or "Remove"). */
    public String getButtonText(String productName) {
        String slug = toSlug(productName);
        return getText(By.cssSelector("[data-test='add-to-cart-" + slug + "'], [data-test='remove-" + slug + "']"));
    }

    /**
     * Waits until the cart badge shows {@code expected} (or disappears when 0).
     * Does not throw on timeout, so the assertion that follows reports the actual count.
     */
    public InventoryPage waitForCartCount(int expected) {
        try {
            if (expected == 0) {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
            } else {
                wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(expected)));
            }
        } catch (TimeoutException e) {
            log.warn("Cart badge did not reach {} within {}", expected, timeout);
        }
        return this;
    }

    public int getCartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText().trim());
    }

    public InventoryPage sortBy(SortOption option) {
        log.info("Sort by: {}", option.label);
        new Select(waitForVisible(sortDropdown)).selectByValue(option.value);
        return this;
    }

    public List<String> getProductNames() {
        waitForVisible(itemNames);
        return getTexts(itemNames);
    }

    public List<BigDecimal> getProductPrices() {
        waitForVisible(itemPrices);
        return getTexts(itemPrices).stream().map(BasePage::toMoney).toList();
    }

    public List<String> getProductImageSources() {
        waitForVisible(itemImages);
        return driver.findElements(itemImages).stream().map(img -> img.getAttribute("src")).toList();
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    public LoginPage logout() {
        log.info("Logging out");
        click(menuButton);
        click(logoutLink);
        return new LoginPage(driver);
    }
}
