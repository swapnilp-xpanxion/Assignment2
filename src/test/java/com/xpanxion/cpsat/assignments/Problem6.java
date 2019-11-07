package com.xpanxion.cpsat.assignments;

import com.xpanxion.cpsat.driver.WebDriverUtil;
import com.xpanxion.cpsat.pages.NSEIndiaPage;
import com.xpanxion.cpsat.pojo.NSECompanyPojo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.*;

/*
Use Chrome to do the following Open https://www.nseindia.com/products.htm
    a) Hover on Live Market
    b) Click on Top Ten Gainers / Losers
    c) Store the Top gainers table values in an excel sheet
    d) Store all the top losers table values in an excel sheet
    e) Assert if the percentage change is high to low for each of Top gainers and losers
    f) Extract the date and time when top gainers and top losers data was taken from
        the NSE website and compare with the system time
 */
public class Problem6 {
    private WebDriver driver;

    @BeforeMethod
    public void initializeDriver() {
        driver = new WebDriverUtil().getDriver("CHROME");
    }

    @Test
    public void verifyTopGainersnadLosersInfo() throws ParseException {
        //Use Chrome to do the following Open https://www.nseindia.com/products.htm
        driver.get("https://www.nseindia.com/products.htm");

        //Hover on Live Market
        NSEIndiaPage nseIndiaPage = new NSEIndiaPage(driver);
        nseIndiaPage.hoverOnLiveMarket();

        //Click on Top Ten Gainers / Losers
        nseIndiaPage.clickTopTenGainerLosersLink();

        //Store the Top gainers table values in an excel sheet
        List<NSECompanyPojo> gainerCompanyInfo = nseIndiaPage.getTop10GainerCompaniesInfo();
        addCompaniesToExcel(gainerCompanyInfo, "Top10Gainers");

        //Store all the top losers table values in an excel sheet
        nseIndiaPage.clickLosersTab();
        List<NSECompanyPojo> loserCompanyInfo = nseIndiaPage.getTop10LoserCompaniesInfo();
        addCompaniesToExcel(loserCompanyInfo, "Top10Losers");

        //Assert if the percentage change is high to low for each of Top gainers and losers
        List<Double> actualGainerPercentChanges = new ArrayList<Double>();
        for (NSECompanyPojo company : gainerCompanyInfo) {
            actualGainerPercentChanges.add(Double.parseDouble(company.getPercentChange()));
        }
        List<Double> expectedGainerPercentChanges = new ArrayList<Double>(actualGainerPercentChanges);
        Collections.sort(expectedGainerPercentChanges, Collections.reverseOrder());
        Assert.assertTrue(expectedGainerPercentChanges.equals(actualGainerPercentChanges), "Top 10 Gainer companies % change is not in descending order.");

        List<Double> actualLoserPercentChanges = new ArrayList<Double>();
        for (NSECompanyPojo company : loserCompanyInfo) {
            actualLoserPercentChanges.add(Double.parseDouble(company.getPercentChange()));
        }
        List<Double> expectedLoserPercentChanges = new ArrayList<Double>(actualLoserPercentChanges);
        Collections.sort(expectedLoserPercentChanges);
        ;
        Assert.assertTrue(expectedLoserPercentChanges.equals(actualLoserPercentChanges), "Top 10 Loser companies % change is not in descending order.");

        //Extract the date and time when top gainers and top losers data was taken from
        //the NSE website and compare with the system time
        Date dataTime = nseIndiaPage.getDataTime();
        Date systemTime = new Date();
        if (systemTime.compareTo(dataTime) == 1) {
            System.out.println("System time is greater than data time.");
        } else {
            System.out.println("System time is less than data time.");
        }

    }

    public void addCompaniesToExcel(List<NSECompanyPojo> companiesInfo, String fileName) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(fileName);
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Symbol");
            row.createCell(1).setCellValue("LTP");
            row.createCell(2).setCellValue("% Change");
            row.createCell(3).setCellValue("Traded Qty");
            row.createCell(4).setCellValue("Value (In Lakhs)");
            row.createCell(5).setCellValue("Open");
            row.createCell(6).setCellValue("High");
            row.createCell(7).setCellValue("Low");
            row.createCell(8).setCellValue("Prev. Close");
            row.createCell(9).setCellValue("Latest Ex Date");

            int rownum = 1;
            for (NSECompanyPojo company : companiesInfo) {
                row = sheet.createRow(rownum++);
                row.createCell(0).setCellValue(company.getSymbol());
                row.createCell(1).setCellValue(company.getLtp());
                row.createCell(2).setCellValue(company.getPercentChange());
                row.createCell(3).setCellValue(company.getTradedQuantity());
                row.createCell(4).setCellValue(company.getValueLakhs());
                row.createCell(5).setCellValue(company.getOpen());
                row.createCell(6).setCellValue(company.getHigh());
                row.createCell(7).setCellValue(company.getLow());
                row.createCell(8).setCellValue(company.getPrevClose());
                row.createCell(9).setCellValue(company.getLatestExDate());
            }
            FileOutputStream out = new FileOutputStream(new File("./exportedData/" + fileName + ".xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }
}
