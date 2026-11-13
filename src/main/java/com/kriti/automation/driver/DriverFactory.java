package com.kriti.automation.driver;

import com.kriti.automation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Map;

/**
 * Creates and holds one WebDriver per thread (ThreadLocal), so tests can later run in parallel.
 * Driver binaries are resolved automatically by Selenium Manager (Selenium 4.6+).
 */
public final class DriverFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");
        LOG.info("Starting {} (headless={})", browser, headless);

        WebDriver driver = switch (browser) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                yield new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        // Explicit waits only - mixing implicit and explicit waits causes unpredictable timing.
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeout")));
        if (!headless) {
            driver.manage().window().maximize();
        }
        DRIVER.set(driver);
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        options.addArguments("--window-size=1920,1080", "--disable-features=PasswordLeakDetection");
        // Stops Chrome's "change your password" popup, which blocks clicks on SauceDemo.
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        return options;
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialised - call DriverFactory.initDriver() first");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (RuntimeException e) {
                LOG.warn("Browser did not quit cleanly: {}", e.getMessage());
            } finally {
                DRIVER.remove();
            }
        }
    }
}
