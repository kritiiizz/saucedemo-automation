package com.kriti.automation.bdd;

import com.kriti.automation.driver.DriverFactory;
import com.kriti.automation.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void startBrowser(Scenario scenario) {
        LOG.info("=== Scenario: {}", scenario.getName());
        DriverFactory.initDriver();
    }

    /** Attaches a screenshot (shown in both the Cucumber and Allure reports). Never hides the real failure. */
    @After
    public void closeBrowser(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                scenario.attach(ScreenshotUtil.captureBytes(DriverFactory.getDriver()), "image/png", scenario.getName());
            }
        } catch (Exception e) {
            LOG.warn("Could not capture screenshot for '{}': {}", scenario.getName(), e.getMessage());
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
