package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object da tela pós-login (Dashboard / Área Segura).
 */
public class DashboardPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(DashboardPage.class);

    @FindBy(css = "h2")
    private WebElement pageHeading;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return wait.waitForUrlContains("secure") && isDisplayed(logoutButton);
    }

    public String getHeadingText() {
        return getText(pageHeading);
    }

    public String getSuccessFlashText() {
        return getText(flashMessage);
    }

    public LoginPage logout() {
        log.info("Realizando logout");
        click(logoutButton);
        return new LoginPage(driver);
    }
}
