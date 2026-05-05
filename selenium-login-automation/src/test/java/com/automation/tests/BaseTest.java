package com.automation.tests;

import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeEach
    void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();

        driver.manage().timeouts()
              .implicitlyWait(Duration.ofSeconds(ConfigReader.implicitWait()));
        driver.manage().window().maximize();

        String url = ConfigReader.baseUrl();
        driver.get(url);
        log.info("Navegando para: {}", url);

        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        DriverFactory.quitDriver();
    }
}
