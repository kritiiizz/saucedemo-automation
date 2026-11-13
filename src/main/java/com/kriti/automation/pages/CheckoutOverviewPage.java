package com.kriti.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

/** Checkout step two: order summary with prices, tax and total. */
public class CheckoutOverviewPage extends BasePage {

    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        waitForVisible(finishButton);
    }

    public List<String> getItemNames() {
        return getTexts(itemNames);
    }

    public BigDecimal sumOfItemPrices() {
        return getTexts(itemPrices).stream().map(BasePage::toMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getItemTotal() {
        return toMoney(getText(subtotalLabel));
    }

    public BigDecimal getTax() {
        return toMoney(getText(taxLabel));
    }

    public BigDecimal getTotal() {
        return toMoney(getText(totalLabel));
    }

    public CheckoutCompletePage finish() {
        log.info("Finishing order");
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }
}
