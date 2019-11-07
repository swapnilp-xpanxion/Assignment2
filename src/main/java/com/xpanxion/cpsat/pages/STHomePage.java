package com.xpanxion.cpsat.pages;

import com.xpanxion.cpsat.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class STHomePage extends BasePage {

    private By firstSlide = By.id("slick-slide00");
    private By slideNextButton = By.cssSelector(".dy-next-arrow.slick-arrow");
    private By menFragrancesSubCategory = By.cssSelector(".ssl-homepage-nav ul.lvl1 > li:nth-child(4) ul.lvl2 > li:nth-child(6)");
    private By allStoresLink = By.linkText("All Stores");

    public STHomePage(WebDriver driver) {
        super(driver);
    }

    public void clickNextSlideTillFirstSlideRepeats() {
        do {
            Actions action = new Actions(driver);
            action.click(waitForElement(slideNextButton)).pause(Duration.ofSeconds(1)).perform();
            System.out.println("Clicked Next Slide Button");
        } while (!driver.findElement(firstSlide).getAttribute("class").contains("slick-current slick-active"));
    }

    public List<String> getAllAccessoriesUnderMensFragrances() {
        List<String> accessories = new ArrayList<>();
        String menCategory = ".ssl-homepage-nav ul.lvl1 > li:nth-child(4)";
        String jquery = "jQuery('" + menCategory + "').mouseover();";
        getJavaScriptExecutor().executeScript(jquery);
        waitForElement(menFragrancesSubCategory).click();
        List<WebElement> elements = waitForElement(menFragrancesSubCategory)
                .findElements(By.cssSelector(".lvl3 > ul > li:nth-child(1) a"));
        for (WebElement ele : elements) {
            accessories.add(ele.getText());
        }
        return accessories;
    }

    public STStoresPage clickAllStoresLink(){
        waitForElement(allStoresLink).click();
        return new STStoresPage(driver);
    }
}
