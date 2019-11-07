package com.xpanxion.cpsat.assignments;

import com.xpanxion.cpsat.driver.WebDriverUtil;
import com.xpanxion.cpsat.pages.NSEIndiaPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
3. Using TestNG and WebDriver script, open https://www.nseindia.com/ in Firefox and do the below
    a. In the Equity window, reference image attached.
    b. Enter the company name Eicher Motors Limited
    c. Click on the magnifying glass or hit enter
    d. A new page opens up with the details of the company
    e. Take screen shot of the searched equity
    f. Fetch and Print the following on the console
     1. Face Value
     2. 52 week high
     3. 52 week low

 */
public class Problem3 {
    private WebDriver driver;

    @BeforeMethod
    public void initializeDriver() {
        driver = new WebDriverUtil().getDriver("FIREFOX");
    }

    @Test
    public void getCompanyDetails() {

        //open https://www.nseindia.com/ in Firefox
        driver.get("https://www.nseindia.com/");

        //Enter the company name Eicher Motors Limited
        //Click on the magnifying glass or hit enter
        NSEIndiaPage nseIndiaPage = new NSEIndiaPage(driver);
        nseIndiaPage.searchEquity("Eicher Motors Limited");

        //Take screen shot of the searched equity
        nseIndiaPage.waitForCompanyInfoToBeDisplayed();
        nseIndiaPage.takeScreenshot("./screenshots/Problem3.png");

        //Fetch and Print the following on the console
        //1. Face Value
        //2. 52 week high
        //3. 52 week low
        System.out.println("Face Value : " + nseIndiaPage.getFacevalue());
        System.out.println("52 week high : " + nseIndiaPage.get52WeekHigh());
        System.out.println("52 week low : " + nseIndiaPage.get52WeekLow());
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }

}
