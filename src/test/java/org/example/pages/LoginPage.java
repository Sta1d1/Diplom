package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница входа — открывается через клик «Вход и регистрация» на главной.
 * React Router перенаправляет на /login, но прямой переход по URL
 * не рендерит форму (требуется клик для инициализации состояния).
 */
public class LoginPage extends BasePage {

    private final SelenideElement authButton = $(byXpath("//button[text()='Вход и регистрация']"));
    private final SelenideElement emailInput = $(byXpath("//input[@placeholder='Введите Email']"));
    private final SelenideElement passwordInput = $(byXpath("//input[@placeholder='Пароль']"));
    private final SelenideElement loginButton = $(byXpath("//button[text()='Войти']"));
    private final SelenideElement logoutButton = $(byXpath("//button[text()='Выйти']"));

    public LoginPage() {
        super("/");
    }

    public LoginPage openLoginPage() {
        openPage();
        // Клик «Вход и регистрация» — React Router переходит на /login
        authButton.shouldBe(visible).click();
        // Ждём появления поля email — признак загрузки формы
        emailInput.shouldBe(visible);
        return this;
    }

    public LoginPage enterEmail(String email) {
        emailInput.shouldBe(visible).setValue(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInput.shouldBe(visible).setValue(password);
        return this;
    }

    public LoginPage clickLogin() {
        loginButton.shouldBe(visible).click();
        return this;
    }

    public boolean isLoginSuccessful() {
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
