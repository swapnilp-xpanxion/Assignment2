package com.xpanxion.cpsat.common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    private Wait<WebDriver> getWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    protected WebElement waitForElement(By by) {
        getWait().until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by);
    }

    protected List<WebElement> waitForElements(By by) {
        getWait().until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElements(by);
    }

    protected void waitForElementsToBeVisible(By by) {
        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        driver.findElement(by);
    }

    protected JavascriptExecutor getJavaScriptExecutor() {
        return (JavascriptExecutor) driver;
    }

    public boolean getResponseCode(String urlString) {
        boolean isValid = false;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                isValid = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public void takeScreenshot(String filePath) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(filePath);
            destFile.delete();
            FileUtils.copyFile(file, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
