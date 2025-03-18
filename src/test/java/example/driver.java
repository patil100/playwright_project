package example;


import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;


public class driver {
    private static Browser browser;
    private static Playwright playwright;
    private static BrowserContext browserContext;
    private static Page page;


    public static void lunchBrowser() {

        try {

            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));

            browserContext = browser.newContext();
            page = browserContext.newPage();

        } catch (Exception e) {
            System.out.println("browser lunch failed " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create driver", e);
        }
    }


//    public static Page getPage() {
//        if (page == null) {
//
////            throw new IllegalStateException("Browser is not initialized. Call launchBrowser() first.");
//            System.out.println("Browser not initialized. Launching browser...");
//            lunchBrowser();
//        }
//        return page;
//    }





    public static Page getPage() {
        if (page == null) {
            System.out.println(" Browser not initialized. Launching browser...");
            lunchBrowser();
        }
        if (page == null) {
            throw new IllegalStateException("Browser failed to initialize even after launching!");
        }
        return page;
    }
    public static Locator getElementByRole(AriaRole role, String textname) {
        return getPage().getByRole(role, new Page.GetByRoleOptions().setName(textname));
    }

    public static void fillElementByTextBoX(String textname, String value) {
        getElementByRole(AriaRole.TEXTBOX, textname).fill(value);
    }

    public static void clickElementByTextBoX(String textname) {
        Locator element=getElementByRole(AriaRole.TEXTBOX, textname);
        element.click();
    }

    public static void clickElementByLINK(String textname) {
        Locator element=getElementByRole(AriaRole.LINK, textname);
        element.click();
    }






    public static void clickElementByButton(String textname) {
        Locator element=getElementByRole(AriaRole.BUTTON, textname);
        element.click();
    }

    public static void visiableElementByButton(String textname) {
        Locator element=getElementByRole(AriaRole.BUTTON, textname);
        element.isVisible();
    }

    public static void enableElementByButton(String textname) {
        Locator element=getElementByRole(AriaRole.BUTTON, textname);
        element.isEnabled();
    }

    public static void closeBrowser() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


}
