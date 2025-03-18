package example;

import Utility.CsvReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;


public class merchant {

    private Page page;

    private final static Logger logger = LogManager.getLogger(merchant.class);

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


    @Test(priority = 1, enabled = true, dependsOnMethods = "testLogin")
    public void staticQR() {
        driver.getPage().waitForTimeout(5000);
        driver.clickElementByButton("Static QR Code");
        System.out.println("Static QR page");
        try {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("storeName")).click();


//
        } catch (Exception e) {
            System.out.println("unable to created Static QR" + e.getMessage());
        }
    }

    @Test(priority = 2, dependsOnMethods = "testLogin")
    public void paymentlink() {
        driver.clickElementByButton("Payment Links");
        driver.getPage().waitForTimeout(4000);
        page.getByText("Create Media Based Payment").click();
        try {
            page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Create Media Based Payment")).click();
            driver.fillElementByTextBoX("First Name", "swapnil");
            driver.fillElementByTextBoX("Last Name", "patil");
            driver.fillElementByTextBoX("Order id", "232");
            driver.fillElementByTextBoX("Product", "4543");
            page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Type")).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Email ID")).click();
            driver.clickElementByTextBoX("Enter Email");
            driver.fillElementByTextBoX("Enter Email", "swapnilp@touras.co");
            driver.fillElementByTextBoX("Amount", "222");
            driver.clickElementByButton("Send Invite");
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

