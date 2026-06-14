package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница регистрации — открывается через «Вход и регистрация» → «Нет аккаунта».
 */
public class RegistrationPage extends BasePage {

    private final SelenideElement authButton = $(byXpath("//button[text()='Вход и регистрация']"));
    private final SelenideElement noAccountButton = $(byXpath("//button[text()='Нет аккаунта']"));
    private final SelenideElement emailInput = $(byXpath("//input[@placeholder='Введите Email']"));
    private final SelenideElement passwordInput = $(byXpath("//input[@placeholder='Пароль']"));
    private final SelenideElement confirmPasswordInput = $(byXpath("//input[@placeholder='Повторите пароль']"));
    private final SelenideElement createAccountButton = $(byXpath("//button[text()='Создать аккаунт']"));
    private final SelenideElement logoutButton = $(byXpath("//button[text()='Выйти']"));

    public RegistrationPage() {
        super("/");
    }

    public RegistrationPage openRegistrationPage() {
        openPage();
        authButton.shouldBe(visible).click();
        emailInput.shouldBe(visible);
        noAccountButton.shouldBe(visible).click();
        confirmPasswordInput.shouldBe(visible);
        return this;
    }

    public RegistrationPage enterEmail(String email) {
        emailInput.shouldBe(visible).setValue(email);
        return this;
    }

    public RegistrationPage enterPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public RegistrationPage confirmPassword(String password) {
        confirmPasswordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public boolean isRegistrationSuccessful() {
        try {
            logoutButton.shouldBe(visible, Duration.ofSeconds(15));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        SelenideElement errorElement = $(byXpath("//*[contains(@class,'error') or contains(@class,'Error') or contains(@class,'err')]"));
        if (errorElement.exists() && errorElement.is(visible)) {
            return errorElement.getText();
        }
        SelenideElement errorText = $(byXpath("//*[contains(text(),'ошиб') or contains(text(),'Ошиб') or contains(text(),'существ') or contains(text(),'Существ')]"));
        if (errorText.exists() && errorText.is(visible)) {
            return errorText.getText();
        }
        return "";
    }
}
