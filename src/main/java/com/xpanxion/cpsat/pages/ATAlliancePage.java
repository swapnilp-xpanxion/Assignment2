package com.xpanxion.cpsat.pages;

import com.xpanxion.cpsat.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class ATAlliancePage extends BasePage {

    private By certificationMenuLink = By.xpath(".//nav//li//a[text()='Certifications']");
    private By certificationIcons = By.cssSelector("map[name='image-map'] area");
    private By cpCCTIcon = By.cssSelector("map[name='image-map'] area[title='CP-CCT']");

    public ATAlliancePage(WebDriver driver) {
        super(driver);
    }

    public void clickCertificationMenu() {
        waitForElement(certificationMenuLink).click();
    }

    public int getCertificationIconsCount() {
        return waitForElements(certificationIcons).size();
    }

    public String getCertificationIconsURLByIndex(int index) {
        return waitForElements(certificationIcons).get(index).getAttribute("href");
    }

    public void hoverOnCpCCT() {
        Actions action = new Actions(driver);
        action.moveToElement(waitForElement(cpCCTIcon)).pause(Duration.ofSeconds(1)).perform();
    }
}
