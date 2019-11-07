package com.xpanxion.cpsat.pages;

import com.xpanxion.cpsat.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class STStoresPage extends BasePage {

    private By citiesDropdown = By.cssSelector(".select-option");
    private By citiesOptions = By.cssSelector("#city-name option");

    STStoresPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAllCitiesNames() {
        List<String> cities = new ArrayList<>();
        waitForElement(citiesDropdown).click();
        for (WebElement ele : driver.findElements(citiesOptions)) {
            cities.add(ele.getText());
        }
        return cities;
    }
}
