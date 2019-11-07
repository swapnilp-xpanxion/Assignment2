package com.xpanxion.cpsat.assignments;

import com.xpanxion.cpsat.driver.WebDriverUtil;
import com.xpanxion.cpsat.pages.ATAlliancePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/*
1. Using Junit and WebDriver script, Go To http://agiletestingalliance.org/ in Google Chrome and do the below.
    a. Click on the certification’s menu item
    b. Count the number of certification icons visible on the page -
       colour icons as per the below image. Total 12.
      Print the URL every image is pointing to.
    c. Confirm if the URL’s are working or not. If the URL is broken highlight that in a soft Assert
    d. Take a screenshot
    e. Hover on CP-CCT
    f. Take a screenshot after hovering such that it shows the CP-CCT on the stored
    screenshot image.
*/
public class Problem1 {
    private WebDriver driver;

    @Before
    public void initializeDriver() {
        driver = new WebDriverUtil().getDriver("CHROME");
    }

    @Test
    public void verifyCertificationIcons() {
        //Go To http://agiletestingalliance.org/ in Google Chrome
        driver.get("http://agiletestingalliance.org/");

        //Click on the certification’s menu item
        ATAlliancePage atAlliancepage = new ATAlliancePage(driver);
        atAlliancepage.clickCertificationMenu();

        //Count the number of certification icons visible on the page
        int certificationIconsCount = atAlliancepage.getCertificationIconsCount();
        System.out.println("Certification Icons Count:" + certificationIconsCount);

        //Print the URL every image is pointing to.
        for (int index = 0; index < certificationIconsCount; index++) {
            System.out.println(atAlliancepage.getCertificationIconsURLByIndex(index));
        }

        //Confirm if the URL’s are working or not.
        // If the URL is broken highlight that in a soft Assert
        for (int index = 0; index < certificationIconsCount; index++) {
            Assert.assertTrue("Broken URL: " + atAlliancepage.getCertificationIconsURLByIndex(index),
                    atAlliancepage.getResponseCode(atAlliancepage.getCertificationIconsURLByIndex(index)));
        }

        //Take a screenshot
        atAlliancepage.takeScreenshot("./screenshots/Problem1-screenshot1.png");

        //Hover on CP-CCT
        atAlliancepage.hoverOnCpCCT();

        //Take a screenshot after hovering such that it shows the CP-CCT on the stored
        atAlliancepage.takeScreenshot("./screenshots/Problem1-Hover-screenshot2.png");
    }

    @After
    public void quitDriver() {
        driver.quit();
    }
}
