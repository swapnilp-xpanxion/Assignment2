package com.xpanxion.cpsat.assignments;

import com.xpanxion.cpsat.driver.WebDriverUtil;
import com.xpanxion.cpsat.pages.STHomePage;
import com.xpanxion.cpsat.pages.STStoresPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/*
5. Using Page object Model, TestNG and WebDriver script, open https://www.shoppersstop.com/ in Google Chrome and do the below:
    a. Click on the banner slider > for the number of times till the banner gets repeated
    b. Print all the accessories name under MEN section > Men’s Fragrance
    c. Click on All Stores link
    d. Print the Cities name that available in Find Stores in your city
    e. Assert Agra, Bhopal and Mysore are available in Find Stores in your city.
    f. Print the page title in console
 */
public class Problem5 {
    private WebDriver driver;

    @BeforeMethod
    public void initializeDriver() {
        driver = new WebDriverUtil().getDriver("CHROME");
    }

    @Test
    public void verifyShoppersStop() {

        //open https://www.shoppersstop.com/ in Google Chrome
        driver.get("https://www.shoppersstop.com/");

        //Click on the banner slider > for the number of times till the banner gets repeated
        STHomePage homePage = new STHomePage(driver);
        homePage.clickNextSlideTillFirstSlideRepeats();

        //Print all the accessories name under MEN section > Men’s Fragrance
        System.out.println(homePage.getAllAccessoriesUnderMensFragrances());

        //Click on All Stores link
        STStoresPage storesPage = homePage.clickAllStoresLink();

        //Print the Cities name that available in Find Stores in your city
        List<String> cityNames = storesPage.getAllCitiesNames();
        System.out.println(cityNames);

        //Assert Agra, Bhopal and Mysore are available in Find Stores in your city.
        Assert.assertTrue(cityNames.contains("Agra"), "Agra is not available in Find Stores in your city");
        Assert.assertTrue(cityNames.contains("Bhopal"), "Bhopal is not available in Find Stores in your city");
        Assert.assertTrue(cityNames.contains("Mysore"), "Mysore is not available in Find Stores in your city");

        //Print the page title in console
        System.out.println("Page Title: " + driver.getTitle());
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }

}
