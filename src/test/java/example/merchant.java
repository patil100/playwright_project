package example;

import Utility.CsvReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import Utility.Random_data.*;

import java.util.List;
import java.util.Map;

import Utility.Random_data;

public class merchant {

    private Page page;
    private final static Logger logger = LogManager.getLogger(merchant.class);
    private static Random_data randomData = new Random_data();

    @BeforeSuite
    public void Setup() {
        driver.lunchBrowser();
        page = driver.getPage();
        logger.info("browser opened");
        page.navigate("https://cbuatportal.tourasuae.com/login");
        logger.info("open Cross border page");
        driver.getPage().waitForTimeout(5000);
        if (driver.getPage() == null) {
            throw new IllegalStateException("page is not initiated");
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        List<Map<String, String>> csvData = CsvReader.readCsv("src/main/resources/Merchant_Credentails.csv");
        Object[][] data = new Object[csvData.size()][2];
        for (int i = 0; i < csvData.size(); i++) {
            data[i][0] = csvData.get(i).get("UserId");
            data[i][1] = csvData.get(i).get("Password");
        }
        return data;
    }

    @Test(dataProvider = "loginData", enabled = true)
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
        driver.getPage().waitForTimeout(2000);
        driver.enableElementByButton("Submit OTP");
        driver.clickElementByButton("Submit OTP");
        driver.getPage().waitForTimeout(2000);
        logger.info("Merchant login Sucessfully");
    }

    private static final String storename = "swapnil" + randomData.randomNumber();
    private static String store_id;

    @Test(priority = 1, groups = "Static QR", enabled = true, dependsOnMethods = "testLogin")
    public void staticQR() {
        store_id="ST" + randomData.randomNumber();
        driver.getPage().waitForTimeout(5000);
        driver.clickElementByButton("Static QR Code");
        logger.info("open static QR page");
        driver.clickElementByButton("Create QR");
        logger.info("click on Create QR button");
        try {
            page.locator("input[name='storeName']").fill(storename);
            page.locator("input[name='store_id']").fill(store_id);
            page.locator("input[name='store_email']").fill("swapnilp@touras.co");
            page.locator("input[id='qr_transaction_amount']").fill("2" + randomData.randomNumber());
            page.locator("input[name='udf']").fill("UDF" + randomData.randomNumber());
            driver.clickElementByButton("Submit");
            logger.info("Static QR created sucessfully");
            Locator snackbar = page.locator("//div[text()='QR code generated']");
            String snackbarText = snackbar.innerText();
            System.out.println("Snackbar text: " + snackbarText);
           Assert.assertTrue(snackbarText.contains(snackbarText), "Snackbar text not found!");
        } catch (Exception e) {
            System.out.println("unable to created Static QR" + e.getMessage());
        }
    }

    @Test(priority = 2, groups = "Static QR", enabled = true, dependsOnMethods = "testLogin")
    public void filter() {
        driver.clickElementByButton("Static QR Code");
//        page.locator("div[id='mui-component-select-search1']").click();
//        page.locator("//*[@data-value=\"Store ID\"]").waitFor();
//        page.locator("//*[@data-value=\"Store ID\"]").click();
//        page.locator("input[name='search']").fill(store_id);
//        driver.clickElementByButton("Search");
//        driver.getPage().waitForTimeout(1000);
//
//        String text = page.locator("td:nth-child(4)").textContent();
//        System.out.println("Element Text: " + text);
//        driver.getPage().waitForTimeout(2000);


        page.locator("//*[@id=\"mui-component-select-status\"]").click();
        driver.getPage().waitForTimeout(1000);
        //-----------verify Active Status
        page.locator("//li[@data-value=\"ACTIVE\"]").click();
        Locator snackbar = page.locator("//li[@data-value=\"ACTIVE\"]");
        String snackbarText = snackbar.innerText();
        System.out.println(snackbarText);
        driver.clickElementByButton("Search");
        driver.getPage().waitForTimeout(1000);
        String text = page.locator("//*[@id=\"__next\"]/div/main/div[4]/div/table/tbody/tr[1]/td[9]/div/p").textContent();
        System.out.println("Element Text: " + text);
        driver.getPage().waitForTimeout(2000);
        Assert.assertTrue(snackbarText.contains(text), "Active status filter not working !");
      // verify Inactive Status
        page.locator("//*[@id=\"mui-component-select-status\"]").click();
        driver.getPage().waitForTimeout(1000);
        page.locator("//li[@data-value=\"INACTIVE\"]").click();
        Locator Filter = page.locator("//li[@data-value=\"INACTIVE\"]");
        String InactiveFilter = Filter.innerText();
        System.out.println(InactiveFilter);
        driver.clickElementByButton("Search");
        driver.getPage().waitForTimeout(1000);
        String InactiveFilter1 = page.locator("//*[@id=\"__next\"]/div/main/div[4]/div/table/tbody/tr[1]/td[9]/div/p").textContent();
        System.out.println("F: " + InactiveFilter1);
        driver.getPage().waitForTimeout(2000);
        Assert.assertTrue(InactiveFilter.contains(InactiveFilter1), "InActive status filter not working !");


    }

    @Test(priority = 2,enabled = false, dependsOnMethods = "testLogin")
    public void paymentlink() {

        driver.clickElementByButton("Payment Links");
        driver.getPage().waitForTimeout(4000);
        page.getByText("Create Media Based Payment").click();
        try {
            page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Create Media Based Payment")).click();

            driver.fillElementByTextBoX("First Name", "swapnil" + randomData.randomNumber());
            driver.fillElementByTextBoX("Last Name", "patil" + randomData.randomNumber());
            driver.fillElementByTextBoX("Order id", "1" + randomData.randomNumber());
            driver.fillElementByTextBoX("Product", "00" + randomData.randomNumber());
            page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Type")).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Email ID")).click();
            driver.clickElementByTextBoX("Enter Email");
            driver.fillElementByTextBoX("Enter Email", "swapnilp@touras.co");
            driver.fillElementByTextBoX("Amount", "2" + randomData.randomNumber());
            driver.clickElementByButton("Send Invite");
            logger.info("payment link craeted sucessfully");
        } catch (Exception e) {
            System.out.println("unable to create payment link----" + e.getMessage());

        }
    }

    @AfterSuite
    public void tearDown() {
        driver.closeBrowser();
        logger.info("browser closed");
    }
}

