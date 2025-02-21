package Utility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ActionableFunctions {
    protected Page page;

    // Constructor
    public ActionableFunctions(Page page) {
        this.page = page;
    }

    // Generic method to get elements by role and name
    public Locator getElementByRole(AriaRole role, String textname) {
        return page.getByRole(role, new Page.GetByRoleOptions().setName(textname));
    }

    public void fillElementFunction(String textname,String value) {
        getElementByRole(AriaRole.TEXTBOX, textname).fill(value);
    }
    public void clickElementFunction(String textname,String value) {
        getElementByRole(AriaRole.BUTTON, textname).fill(value);
    }
}

