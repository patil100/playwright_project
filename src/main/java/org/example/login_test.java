package org.example;

import org.example.driver;
import Utility.CsvReader;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.example.driver.clickElementByTextBoX;
import static org.example.driver.fillElementByTextBoX;
import static org.example.driver.clickElementByButton;
import static org.example.driver.enableElementByButton;
import static org.example.driver.visiableElementByButton;


public class login_test {

    private Page page;

    private final static Logger logger = LogManager.getLogger(login_test.class);

    @BeforeTest
    public void Setup() {
        driver.lunchBrowser();
        page = driver.getPage();
        logger.info("browser opened");
        page.navigate("https://cbuatportal.tourasuae.com/login");
        logger.info("open Cross border page");
        driver.getPage().waitForTimeout(5000);
        if (driver.getPage() == null)
        {
            throw new IllegalStateException("page is not initiated");
        }
    }


        @Test(priority = 1, dataProvider = "loginData")
        public void testLogin(String userId, String password) {
            driver.clickElementByTextBoX("Enter User Id");
            driver.fillElementByTextBoX("Enter User Id", userId);
            driver.clickElementByTextBoX("Enter Password");
            driver.fillElementByTextBoX("Enter Password", password);
            driver.clickElementByButton("Login");
            driver.getPage().waitForTimeout(2000);
            driver.getPage().locator("#otp_0").press("1");
            driver.getPage().locator("#otp_1").press("2");
            driver.getPage().locator("#otp_2").press("3");
            driver.getPage().locator("#otp_3").press("4");
            driver.getPage().waitForTimeout(5000);
            driver.enableElementByButton("Submit OTP");
            driver.clickElementByButton("Submit OTP");
            driver.getPage().waitForTimeout(5000);
            logger.info("login Sucessfully");

        }


    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
    List<Map<String, String>> csvData = CsvReader.readCsv("src/main/resources/Admin_Credentails.csv");
    Object[][] data = new Object[csvData.size()][2];
        for (int i = 0; i < csvData.size(); i++) {
            data[i][0] = csvData.get(i).get("UserId");
            data[i][1] = csvData.get(i).get("Password");
        }
        return data;
    }

    @Test(priority = 2)
    public void merchantManagement() {
        driver.getPage().waitForTimeout(5000);
        driver.visiableElementByButton("Merchant Management");
        driver.clickElementByButton("Merchant Management");
        driver.clickElementByButton("All Merchants");
        logger.info("All merchant page opened");
        driver.clickElementByButton("Add New Merchant");
        driver.getPage().waitForTimeout(5000);
    }

    public void createMerchant(){
        driver.clickElementByTextBoX("Paygate India Pvt Ltd");
        driver.fillElementByTextBoX("Select Aggregator","Paygate India Pvt Ltd");
        driver.getPage().locator("//input[@value=\"Paygate India Pvt Ltd\"]").click();




    }
















    public void tearDown() {
        driver.closeBrowser();
        logger.info("browser closed");
    }
}

