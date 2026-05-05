package com.automation.pages;

import com.automation.utils.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe base para todas as Page Objects.
 * Centraliza o PageFactory e os utilitários comuns.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitHelper wait;
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    protected void type(WebElement element, String text) {
        wait.waitForVisible(element);
        element.clear();
        element.sendKeys(text);
        log.debug("Digitado '{}' no elemento {}", text, element);
    }

    protected void click(WebElement element) {
        wait.waitForClickable(element);
        element.click();
        log.debug("Clicado no elemento {}", element);
    }

    protected String getText(WebElement element) {
        wait.waitForVisible(element);
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
