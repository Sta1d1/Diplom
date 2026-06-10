package org.example.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.example.config.Config;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void setUp() {
        Configuration.browser = Config.getBrowser();
        Configuration.timeout = Config.getTimeout();
        Configuration.headless = Config.isHeadless();
        Configuration.baseUrl = Config.getBaseUrl();
    }

    @After
    public void tearDown(io.cucumber.java.Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                if (WebDriverRunner.hasWebDriverStarted()) {
                    byte[] screenshotBytes = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                            .getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Screenshot on failure",
                            new ByteArrayInputStream(screenshotBytes));
                }
            } catch (Exception e) {
                Allure.addAttachment("Screenshot error", e.getMessage());
            }
        }
        Selenide.closeWebDriver();
    }
}
