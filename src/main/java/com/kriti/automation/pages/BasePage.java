package com.kriti.automation.pages;

import com.kriti.automation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

/** Shared wait-based actions so every page interacts with elements the same, reliable way. */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Duration timeout;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.timeout = Duration.ofSeconds(ConfigReader.getInt("explicitWait"));
        this.wait = new WebDriverWait(driver, timeout);
    }

    protected WebElement waitForVisible(By locator) {
        log.debug("Waiting for {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        log.debug("Click {}", locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        log.debug("Type '{}' into {}", text, locator);
        WebElement field = waitForVisible(locator);
        field.clear();
        field.sendKeys(text);
    }

    protected String getText(By locator) {
        String text = waitForVisible(locator).getText().trim();
        log.debug("Read '{}' from {}", text, locator);
        return text;
    }

    /** Waits up to {@code timeout} for the element; returns false instead of throwing. */
    protected boolean isDisplayed(By locator, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            log.debug("{} not visible within {}", locator, timeout);
            return false;
        }
    }

    protected boolean isDisplayed(By locator) {
        return isDisplayed(locator, timeout);
    }

    protected List<String> getTexts(By locator) {
        return driver.findElements(locator).stream().map(e -> e.getText().trim()).toList();
    }

    /** Locator for SauceDemo's data-test attributes, which are stable test hooks. */
    protected static By byDataTest(String value) {
        return By.cssSelector("[data-test='" + value.replace("'", "\\'") + "']");
    }

    /**
     * Builds the id SauceDemo uses in data-test attributes from a product name:
     * lower case, whitespace replaced by hyphens.
     * "Sauce Labs Backpack" -> "sauce-labs-backpack",
     * "Test.allTheThings() T-Shirt (Red)" -> "test.allthethings()-t-shirt-(red)".
     */
    protected static String toSlug(String productName) {
        return productName.trim().toLowerCase().replaceAll("\\s+", "-");
    }

    /** Turns "$29.99" or "Item total: $39.98" into 29.99 / 39.98. */
    protected static BigDecimal toMoney(String text) {
        return new BigDecimal(text.replaceAll("[^0-9.]", ""));
    }
}
