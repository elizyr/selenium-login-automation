package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object da tela de Login.
 *
 * Localiza os elementos via @FindBy e expõe ações de alto nível.
 * OBS: os locators abaixo são compatíveis com https://the-internet.herokuapp.com/login
 *      (site público para prática de automação). Ajuste para o seu sistema alvo.
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    // -------------------------------------------------------------------------
    // Mapeamento de elementos (@FindBy)
    // -------------------------------------------------------------------------

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    @FindBy(css = ".flash.error")
    private WebElement errorMessage;

    @FindBy(css = ".flash.success")
    private WebElement successMessage;

    // -------------------------------------------------------------------------
    // Construtor
    // -------------------------------------------------------------------------

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // -------------------------------------------------------------------------
    // Ações de alto nível
    // -------------------------------------------------------------------------

    /* Preenche usuário e senha e clica em Login */
    public void login(String username, String password) {
        log.info("Realizando login com usuário: '{}'", username);
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    /* Preenche apenas o campo de usuário (sem senha) */
    public void fillUsernameOnly(String username) {
        type(usernameInput, username);
        click(loginButton);
    }

    /* Preenche apenas o campo de senha (sem usuário) */
    public void fillPasswordOnly(String password) {
        type(passwordInput, password);
        click(loginButton);
    }

    /* Submete o formulário vazio */
    public void submitEmpty() {
        click(loginButton);
    }

    /* Getters de estado / assertivas */

    public String getFlashMessageText() {
        return getText(flashMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage);
    }

    public boolean isUsernameFieldVisible() {
        return isDisplayed(usernameInput);
    }

    public boolean isPasswordFieldVisible() {
        return isDisplayed(passwordInput);
    }
}
