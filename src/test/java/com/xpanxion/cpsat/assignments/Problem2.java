package com.xpanxion.cpsat.assignments;

import com.xpanxion.cpsat.driver.WebDriverUtil;
import com.xpanxion.cpsat.pages.NSEIndiaPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/*
2) Using TestNG and WebDriver script, open https://www.nseindia.com/ in Google Chrome and do the below
    a. Using FindElements method of webdriver get the advances, Declines and
        Unchanged numbers from the maket watch window - reference image
    b. Print the Minimum number
        e.g. Unchanged 0
*/
public class Problem2 {
    private WebDriver driver;

    @BeforeMethod
    public void initializeDriver() {
        driver = new WebDriverUtil().getDriver("CHROME");
    }

    @Test
    public void getMininumOnNSEIndiaPage() {
        //open https://www.nseindia.com/ in Google Chrome
        driver.get("https://www.nseindia.com/");

        //get the advances, Declines and Unchanged numbers from the maket watch window
        NSEIndiaPage nseIndiaPage = new NSEIndiaPage(driver);
        List<WebElement> advancesInfo = nseIndiaPage.getAdvancesInfo();
        int advances = Integer.parseInt(advancesInfo.get(0).getText());
        int declines = Integer.parseInt(advancesInfo.get(1).getText());
        int unchanged = Integer.parseInt(advancesInfo.get(2).getText());

        if (advances > declines) {
            if (declines > unchanged)
                System.out.println("Minimum number: Unchanged " + unchanged);
            else
                System.out.println("Minimum number: Declines " + declines);
        } else {
            if (advances > unchanged)
                System.out.println("Minimum number: Unchanged " + unchanged);
            else
                System.out.println("Minimum number: Advances " + advances);
        }
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }

}
