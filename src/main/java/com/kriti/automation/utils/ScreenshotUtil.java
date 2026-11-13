package com.kriti.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");
    private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtil() {
    }

    /** Saves a PNG to target/screenshots and returns its path. */
    public static Path capture(WebDriver driver, String name) {
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safeName = name.replaceAll("[^a-zA-Z0-9_-]", "_");
            Path target = SCREENSHOT_DIR.resolve(safeName + "_" + LocalDateTime.now().format(STAMP) + ".png");
            Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            return target;
        } catch (IOException e) {
            throw new RuntimeException("Could not save screenshot " + name, e);
        }
    }

    public static byte[] captureBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
