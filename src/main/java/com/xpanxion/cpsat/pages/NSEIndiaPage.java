package com.xpanxion.cpsat.pages;

import com.xpanxion.cpsat.common.BasePage;
import com.xpanxion.cpsat.pojo.NSECompanyPojo;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NSEIndiaPage extends BasePage {

    private By advancesInformation = By.cssSelector(".advanceTab li span");
    private By searchTextBox = By.cssSelector(".search #keyword");
    private By faceValue = By.id("faceValue");
    private By high52 = By.id("high52");
    private By low52 = By.id("low52");
    private By companyInfo = By.id("companies");
    private By suggestions = By.cssSelector("#ajax_response ol li");
    private By liveMarketMenu = By.id("main_livemkt");
    private By topTenGainersLosers = By.id("main_liveany_ttg");
    private By topGainersTableRows = By.cssSelector("#topGainers tbody tr");
    private By topLosersTableRows = By.cssSelector("#topLosers tbody tr");
    private By losersTab = By.id("tab8");
    private By dataRow = By.cssSelector("td");
    private By dataRowLink = By.cssSelector("td a");
    private By dataTime = By.id("dataTime");

    public NSEIndiaPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAdvancesInfo() {
        return waitForElements(advancesInformation);
    }

    public void searchEquity(String name) {
        waitForElement(searchTextBox).sendKeys(name);
        waitForElementsToBeVisible(suggestions);
        waitForElement(searchTextBox).sendKeys(Keys.ENTER);
    }

    public void waitForCompanyInfoToBeDisplayed() {
        waitForElement(companyInfo);
    }

    public String getFacevalue() {
        return waitForElement(faceValue).getText();
    }

    public String get52WeekHigh() {
        return waitForElement(high52).getText();
    }

    public String get52WeekLow() {
        return waitForElement(low52).getText();
    }

    public void hoverOnLiveMarket() {
        new Actions(driver).moveToElement(waitForElement(liveMarketMenu)).perform();
    }

    public void clickTopTenGainerLosersLink() {
        waitForElement(topTenGainersLosers).click();
    }

    public List<NSECompanyPojo> getTop10GainerCompaniesInfo() {
        List<NSECompanyPojo> companyInfo = new ArrayList<>();
        List<WebElement> companyElements = waitForElements(topGainersTableRows);
        for (int i = 1; i < companyElements.size(); i++) {
            NSECompanyPojo company = new NSECompanyPojo();
            company.setSymbol(companyElements.get(i).findElement(dataRowLink).getText());
            company.setLtp(companyElements.get(i).findElements(dataRow).get(1).getText());
            company.setPercentChange(companyElements.get(i).findElements(dataRow).get(2).getText());
            company.setTradedQuantity(companyElements.get(i).findElements(dataRow).get(3).getText());
            company.setValueLakhs(companyElements.get(i).findElements(dataRow).get(4).getText());
            company.setOpen(companyElements.get(i).findElements(dataRow).get(5).getText());
            company.setHigh(companyElements.get(i).findElements(dataRow).get(6).getText());
            company.setLow(companyElements.get(i).findElements(dataRow).get(7).getText());
            company.setPrevClose(companyElements.get(i).findElements(dataRow).get(8).getText());
            company.setLatestExDate(companyElements.get(i).findElements(dataRow).get(9).getText());
            companyInfo.add(company);
        }
        return companyInfo;
    }

    public List<NSECompanyPojo> getTop10LoserCompaniesInfo() {
        List<NSECompanyPojo> companyInfo = new ArrayList<>();
        List<WebElement> companyElements = waitForElements(topLosersTableRows);
        for (int i = 1; i < companyElements.size(); i++) {
            NSECompanyPojo company = new NSECompanyPojo();
            company.setSymbol(companyElements.get(i).findElement(dataRowLink).getText());
            company.setLtp(companyElements.get(i).findElements(dataRow).get(1).getText());
            company.setPercentChange(companyElements.get(i).findElements(dataRow).get(2).getText());
            company.setTradedQuantity(companyElements.get(i).findElements(dataRow).get(3).getText());
            company.setValueLakhs(companyElements.get(i).findElements(dataRow).get(4).getText());
            company.setOpen(companyElements.get(i).findElements(dataRow).get(5).getText());
            company.setHigh(companyElements.get(i).findElements(dataRow).get(6).getText());
            company.setLow(companyElements.get(i).findElements(dataRow).get(7).getText());
            company.setPrevClose(companyElements.get(i).findElements(dataRow).get(8).getText());
            company.setLatestExDate(companyElements.get(i).findElements(dataRow).get(9).getText());
            companyInfo.add(company);
        }
        return companyInfo;
    }

    public void clickLosersTab() {
        waitForElement(losersTab).click();
    }

    public Date getDataTime() throws ParseException {
        String dateTime = waitForElement(dataTime).getText().replace("As on ", "");
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy kk:mm:ss z");
        return format.parse(dateTime);
    }

}
