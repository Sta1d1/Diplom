package org.example.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.api.AuthApi;
import org.example.models.TestDataGenerator;
import org.example.models.User;
import org.example.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Шаги для сценария авторизации.
 */
public class LoginSteps {

    private final User user;
    private final AuthApi authApi;
    private LoginPage loginPage;

    public LoginSteps(User user, AuthApi authApi) {
        this.user = user;
        this.authApi = authApi;
    }

    private LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = Selenide.page(LoginPage.class);
        }
        return loginPage;
    }

    @Дано("пользователь зарегистрирован через API")
    public void userRegisteredViaApi() {
        String email = TestDataGenerator.generateEmail();
        String password = TestDataGenerator.generatePassword();
        try {
            String token = authApi.register(email, password);
            user.setEmail(email);
            user.setPassword(password);
            user.setToken(token);
        } catch (Exception e) {
            user.setEmail(email);
            user.setPassword(password);
        }
    }

    @Дано("пользователь открывает страницу входа")
    public void userOpensLoginPage() {
        loginPage = new LoginPage();
        loginPage.openLoginPage();
    }

    @Когда("вводит email")
    public void entersEmail() {
        getLoginPage().enterEmail(user.getEmail());
    }

    @Когда("вводит пароль на странице входа")
    public void entersPassword() {
        getLoginPage().enterPassword(user.getPassword());
    }

    @Тогда("авторизация проходит успешно")
    public void loginIsSuccessful() {
        assertTrue(getLoginPage().isLoginSuccessful(),
                "Авторизация должна пройти успешно — должна появиться кнопка «Выйти»");
    }
}
