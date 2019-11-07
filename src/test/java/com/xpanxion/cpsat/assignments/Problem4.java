package com.xpanxion.cpsat.assignments;

import com.xpanxion.cpsat.driver.WebDriverUtil;
import com.xpanxion.cpsat.pages.NSEIndiaPage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

/*
4) Using TestNG and WebDriver script, open https://www.nseindia.com/ in Chrome and do the below
    a. In the Equity window enter the company name, reference image attached.
    b. Read the following from an Excel file which has a single column having the
    company names in it. Read the file and for every company name below
        1. Bajaj Finserv Limited
        2. Hindustan Unilever
        3. Mahindra & Mahindra Limited
        4. GAIL
        Enter the company name in the equity text box
    c. Click on the magnifying glass or hit enter
    d. A new page opens up with the details of the company
    e. Fetch and Print the following on the console
     1. Face Value
     2. 52 week high
     3. 52 week low
    f. Take screenshot of the page.
 */
public class Problem4 {
    private WebDriver driver;

    //Dataprovider to get company names from excel sheet
    @DataProvider(name = "getCompanyNames")
    public static Object[][] getCompanyNames() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src//test//resources//NSE_Companies_TestData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet worksheet = workbook.getSheet("Sheet1");
        XSSFRow row = worksheet.getRow(0);
        int rownum = worksheet.getPhysicalNumberOfRows();
        int colnum = row.getLastCellNum();
        Object[][] data = new Object[rownum - 1][colnum];
        for (int i = 0; i < rownum - 1; i++) {
            XSSFRow tempRow = worksheet.getRow(i + 1);
            for (int j = 0; j < colnum; j++) {
                if (tempRow == null)
                    data[i][j] = "";
                else {
                    XSSFCell cell = tempRow.getCell(j);
                    if (cell == null)
                        data[i][j] = "";
                    else {
                        String value = new DataFormatter().formatCellValue(cell);
                        data[i][j] = value;
                    }
                }
            }
        }
        return data;
    }

    @BeforeMethod
    public void initializeDriver() {
        driver = new WebDriverUtil().getDriver("CHROME");
    }

    @Test(dataProvider = "getCompanyNames")
    public void getCompanyInformation(String companyName) {
        //open https://www.nseindia.com/ in Chrome
        driver.get("https://www.nseindia.com/");

        //In the Equity window enter the company name
        //Click on the magnifying glass or hit enter
        NSEIndiaPage nseIndiaPage = new NSEIndiaPage(driver);
        nseIndiaPage.searchEquity(companyName);

        //Fetch and Print the following on the console
        //1. Face Value
        //2. 52 week high
        //3. 52 week low
        nseIndiaPage.waitForCompanyInfoToBeDisplayed();
        System.out.println("Face Value : " + nseIndiaPage.getFacevalue());
        System.out.println("52 week high : " + nseIndiaPage.get52WeekHigh());
        System.out.println("52 week low : " + nseIndiaPage.get52WeekLow());

        //Take screen shot of the searched equity
        nseIndiaPage.takeScreenshot("./screenshots/Problem4_" + companyName + ".png");
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }
}
