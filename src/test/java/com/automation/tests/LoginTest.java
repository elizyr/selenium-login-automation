package com.automation.tests;

import com.automation.pages.DashboardPage;
import com.automation.utils.ConfigReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Casos de teste para o fluxo de autenticação (Login).
 *
 * ┌─────┬────────────────────────────────────────────────┬──────────┐
 * │ TC  │ Cenário                                        │ Tipo     │
 * ├─────┼────────────────────────────────────────────────┼──────────┤
 * │ TC1 │ Login com credenciais válidas                  │ Sucesso  │
 * │ TC2 │ Login com senha incorreta                      │ Falha    │
 * │ TC3 │ Login com usuário inexistente                  │ Falha    │
 * │ TC4 │ Login com campos vazios                        │ Borda    │
 * │ TC5 │ Login apenas com usuário (sem senha)           │ Borda    │
 * │ TC6 │ Login apenas com senha (sem usuário)           │ Borda    │
 * │ TC7 │ Login com credenciais inválidas (parametrizado)│ Falha    │
 * └─────┴────────────────────────────────────────────────┴──────────┘
 */
@DisplayName("Fluxo de Login")
class LoginTest extends BaseTest {

    // =========================================================================
    // TC1 - Login com sucesso (credenciais válidas)
    // =========================================================================

    @Test
    @Tag("smoke")
    @DisplayName("TC1 - Login com credenciais válidas deve redirecionar para o dashboard")
    void tc1_loginWithValidCredentials_shouldNavigateToDashboard() {
        // Given
        String username = ConfigReader.validUsername();
        String password = ConfigReader.validPassword();

        // When
        loginPage.login(username, password);
        DashboardPage dashboardPage = new DashboardPage(driver);

        // Then
        assertThat(dashboardPage.isLoaded())
                .as("Dashboard deve ser carregado após login bem-sucedido")
                .isTrue();

        assertThat(dashboardPage.getSuccessFlashText())
                .as("Mensagem de boas-vindas deve ser exibida")
                .containsIgnoringCase("You logged into a secure area");
    }

    // =========================================================================
    // TC2 - Login com senha incorreta
    // =========================================================================

    @Test
    @Tag("regression")
    @DisplayName("TC2 - Login com senha incorreta deve exibir mensagem de erro")
    void tc2_loginWithWrongPassword_shouldShowErrorMessage() {
        // Given
        String username = ConfigReader.validUsername();
        String wrongPassword = "senhaErrada@123";

        // When
        loginPage.login(username, wrongPassword);

        // Then
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Mensagem de erro deve ser exibida para senha incorreta")
                .isTrue();

        assertThat(loginPage.getFlashMessageText())
                .as("Mensagem deve informar senha inválida")
                .containsIgnoringCase("Your password is invalid");

        assertThat(driver.getCurrentUrl())
                .as("Usuário não deve ser redirecionado do login")
                .doesNotContain("secure");
    }

    // =========================================================================
    // TC3 - Login com usuário inexistente
    // =========================================================================

    @Test
    @Tag("regression")
    @DisplayName("TC3 - Login com usuário inexistente deve exibir mensagem de erro")
    void tc3_loginWithUnknownUser_shouldShowErrorMessage() {
        // Given
        String unknownUser = "usuario_inexistente_xyz";
        String anyPassword = "qualquerSenha";

        // When
        loginPage.login(unknownUser, anyPassword);

        // Then
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Mensagem de erro deve ser exibida para usuário inexistente")
                .isTrue();

        assertThat(loginPage.getFlashMessageText())
                .as("Mensagem deve informar usuário inválido")
                .containsIgnoringCase("Your username is invalid");

        assertThat(driver.getCurrentUrl())
                .as("Usuário não deve ser redirecionado do login")
                .doesNotContain("secure");
    }

    // =========================================================================
    // TC4 - Submissão de formulário vazio (campos em branco)
    // =========================================================================

    @Test
    @Tag("edge-case")
    @DisplayName("TC4 - Submissão com campos vazios deve exibir mensagem de erro")
    void tc4_loginWithEmptyFields_shouldShowErrorMessage() {
        // When
        loginPage.submitEmpty();

        // Then
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Mensagem de erro deve ser exibida ao submeter formulário vazio")
                .isTrue();

        assertThat(loginPage.isUsernameFieldVisible())
                .as("Campo de usuário deve permanecer visível na página de login")
                .isTrue();
    }

    // =========================================================================
    // TC5 - Login apenas com usuário (campo senha em branco)
    // =========================================================================

    @Test
    @Tag("edge-case")
    @DisplayName("TC5 - Login apenas com usuário (sem senha) deve exibir mensagem de erro")
    void tc5_loginWithUsernameOnly_shouldShowErrorMessage() {
        // Given
        String username = ConfigReader.validUsername();

        // When
        loginPage.fillUsernameOnly(username);

        // Then
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Mensagem de erro deve ser exibida quando a senha está vazia")
                .isTrue();

        assertThat(driver.getCurrentUrl())
                .as("Usuário não deve ser redirecionado do login")
                .doesNotContain("secure");
    }

    // =========================================================================
    // TC6 - Login apenas com senha (campo usuário em branco)
    // =========================================================================

    @Test
    @Tag("edge-case")
    @DisplayName("TC6 - Login apenas com senha (sem usuário) deve exibir mensagem de erro")
    void tc6_loginWithPasswordOnly_shouldShowErrorMessage() {
        // Given
        String password = ConfigReader.validPassword();

        // When
        loginPage.fillPasswordOnly(password);

        // Then
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Mensagem de erro deve ser exibida quando o usuário está vazio")
                .isTrue();

        assertThat(driver.getCurrentUrl())
                .as("Usuário não deve ser redirecionado do login")
                .doesNotContain("secure");
    }

    // =========================================================================
    // TC7 - Teste parametrizado: múltiplas combinações de credenciais inválidas
    // =========================================================================

    @ParameterizedTest(name = "TC7 [{index}] usuário=''{0}'' senha=''{1}'' → esperado: ''{2}''")
    @Tag("regression")
    @DisplayName("TC7 - Combinações de credenciais inválidas devem exibir erro")
    @CsvSource({
            "tomsmith,  wrongPass,     Your password is invalid",
            "wrongUser, SuperSecretPassword!, Your username is invalid",
            "' ',       ' ',           Your username is invalid",
            "TOMSMITH,  SuperSecretPassword!, Your username is invalid"
    })
    void tc7_loginWithInvalidCredentialCombinations_shouldShowExpectedError(
            String username,
            String password,
            String expectedErrorFragment) {

        // When
        loginPage.login(username.trim(), password.trim());

        // Then
        assertThat(loginPage.isErrorMessageDisplayed())
                .as("Mensagem de erro deve ser exibida para credenciais inválidas")
                .isTrue();

        assertThat(loginPage.getFlashMessageText())
                .as("Mensagem de erro deve conter: '%s'", expectedErrorFragment)
                .containsIgnoringCase(expectedErrorFragment.trim());
    }
}
