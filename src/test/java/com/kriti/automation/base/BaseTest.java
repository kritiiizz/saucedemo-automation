package com.kriti.automation.base;

import com.kriti.automation.driver.DriverFactory;
import com.kriti.automation.pages.LoginPage;
import com.kriti.automation.utils.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

/** Fresh browser per test so tests never depend on each other. */
public abstract class BaseTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        log.info("=== Starting {}", method.getName());
        DriverFactory.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                captureFailureScreenshot(result.getMethod().getMethodName());
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }

    /**
     * Saves a screenshot to target/screenshots and attaches it to the Allure report.
     * Never throws: if the browser has crashed, a failing screenshot must not hide the real test failure.
     */
    private void captureFailureScreenshot(String testName) {
        try {
            Path shot = ScreenshotUtil.capture(driver(), testName);
            Reporter.log("Failure screenshot: " + shot.toAbsolutePath(), true);
            Allure.addAttachment(testName, "image/png", new ByteArrayInputStream(Files.readAllBytes(shot)), "png");
        } catch (Exception e) {
            log.warn("Could not capture screenshot for {}: {}", testName, e.getMessage());
        }
    }

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    protected LoginPage openLoginPage() {
        return new LoginPage(driver()).open();
    }
}
