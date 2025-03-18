package example;

import Utility.CsvReader;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Admin {

    private Page page;

    private final static Logger logger = LogManager.getLogger(Admin.class);

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
            driver.getPage().waitForTimeout(2000);
            driver.enableElementByButton("Submit OTP");
            driver.clickElementByButton("Submit OTP");
            driver.getPage().waitForTimeout(2000);
            logger.info("Admin login Sucessfully");

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

   // @Test(priority = 2)
    public void merchantManagement() {

        driver.getPage().waitForTimeout(3000);
        driver.visiableElementByButton("merchant Management");
        driver.clickElementByButton("merchant Management");
        driver.clickElementByButton("All Merchants");
        logger.info("All merchant page opened");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("more")).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Aggregator")).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Aggregator")).fill("pay");
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Paygate India Pvt Ltd")).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Parent merchant")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("PG merchant")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter merchant Name")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter merchant Name")).fill("newMerchant");
        page.locator("div:nth-child(4) > div > .MuiBox-root > .MuiAutocomplete-root > .MuiFormControl-root > .MuiInputBase-root").click();
        //page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Reseller")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Business Registered Name")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Business Registered Name")).fill("newME");
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Industry")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("E-Commerce")).click();
       // page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Plan Name")).click();
        page.locator("div:nth-child(6) > div > .MuiBox-root > .MuiAutocomplete-root > .MuiFormControl-root > .MuiInputBase-root").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Sole Trader/ Proprietorship/")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Sub Sector")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Sub Sector")).fill("medical");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Trade License Number")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Trade License Number")).fill("465643436436436436");
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Business Type")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("B2B")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Select Date").setExact(true)).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Choose Tuesday, February 25th,")).dblclick();
        page.locator("textarea[name=\"business_description\"]").click();
        page.locator("textarea[name=\"business_description\"]").fill("Testing");

        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Annual Turnover")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("$ 25-100 Million")).click();
        page.locator("textarea[name=\"legal_office_address\\.\"]").click();
        page.locator("textarea[name=\"legal_office_address\\.\"]").fill("thane");
        page.locator("div:nth-child(12) > div:nth-child(2) > .MuiBox-root > .MuiAutocomplete-root > .MuiFormControl-root > .MuiInputBase-root").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("India")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter city")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter city")).fill("pune");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter State")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter State")).fill("MH");
        page.locator("#pincode").click();
        page.locator("#pincode").fill("416311");
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Processing Currencies")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("IN - INR")).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Settlement Currencies")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("IN - INR")).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Integration Type")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Aggregator Hosted").setExact(true)).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Arrears Days")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("2").setExact(true)).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Payment Cycle")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("T + 1").setExact(true)).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter First Name")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter First Name")).fill("swapnil");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Last Name")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Last Name")).fill("patil");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Select Date of Birth")).click();
        page.getByText("9101112131415").dblclick();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Emirates ID: Starts from")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Emirates ID: Starts from")).fill("784121212121212");
        page.locator("#email_id").click();
        page.locator("#email_id").fill("swapnilp@safexpay.com");
        page.locator("div:nth-child(27) > div:nth-child(2) > .MuiBox-root > .MuiAutocomplete-root > .MuiFormControl-root > .MuiInputBase-root > .MuiAutocomplete-endAdornment > .MuiButtonBase-root").click();
        page.locator("#dial_Code_demo132rds").nth(1).fill("91");
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("+91")).click();
        page.locator("#mobile_no").click();
        page.locator("#mobile_no").fill("9921415238");
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Relationship with the")).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Director").setExact(true)).click();
        page.locator("#compadial_Code_demo132rds").click();
        page.locator("#compadial_Code_demo132rds").fill("91");
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("+91")).click();
        page.locator("#company_phone").click();
        page.locator("#company_phone").fill("9921415238");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter website url")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter website url")).fill("www.goggle.com");
        page.locator("#support_email_id").click();
        page.locator("#support_email_id").fill("swapnilp@safexpay.com");
        page.locator("#notification_email_id").click();
        page.locator("#notification_email_id").fill("swapnilp@safexpay.com");
        page.locator("#notification_email_id").press("Tab");
        driver.clickElementByButton("Save & Continue");



        driver.getPage().waitForTimeout(3000);
    }





    public void tearDown() {
        driver.closeBrowser();
        logger.info("browser closed");
    }
}

