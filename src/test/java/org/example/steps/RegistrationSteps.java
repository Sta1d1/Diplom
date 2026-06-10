package org.example.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.api.AuthApi;
import org.example.models.TestDataGenerator;
import org.example.models.User;
import org.example.pages.RegistrationPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Шаги для сценариев регистрации.
 */
public class RegistrationSteps {

    private final User user;
    private final AuthApi authApi;
    private RegistrationPage registrationPage;
    private static String registeredEmail;

    public RegistrationSteps(User user, AuthApi authApi) {
        this.user = user;
        this.authApi = authApi;
    }

    private RegistrationPage getRegistrationPage() {
        if (registrationPage == null) {
            registrationPage = Selenide.page(RegistrationPage.class);
        }
        return registrationPage;
    }

    @Дано("пользователь открывает страницу регистрации")
    public void userOpensRegistrationPage() {
        registrationPage = new RegistrationPage();
        registrationPage.openRegistrationPage();
    }

    @Когда("вводит уникальный email")
    public void entersUniqueEmail() {
        String email = TestDataGenerator.generateEmail();
        user.setEmail(email);
        registeredEmail = email;
        getRegistrationPage().enterEmail(email);
    }

    @Когда("вводит тот же email")
    public void entersSameEmail() {
        getRegistrationPage().enterEmail(registeredEmail);
    }

    @Когда("указывает пароль")
    public void entersPassword() {
        String password = TestDataGenerator.generatePassword();
        user.setPassword(password);
        getRegistrationPage().enterPassword(password);
    }

    @Когда("подтверждает пароль")
    public void confirmsPassword() {
        getRegistrationPage().confirmPassword(user.getPassword());
    }

    @Когда("указывает неверный пароль при регистрации")
    public void entersWrongPassword() {
        getRegistrationPage().enterPassword("WrongPassword999!");
    }

    @Тогда("регистрация проходит успешно")
    public void registrationIsSuccessful() {
        assertTrue(getRegistrationPage().isRegistrationSuccessful(),
                "Регистрация должна пройти успешно — должна появиться кнопка «Выйти»");
    }

    @Дано("пользователь с email уже зарегистрирован")
    public void userWithEmailAlreadyRegistered() {
        String email = TestDataGenerator.generateEmail();
        String password = TestDataGenerator.generatePassword();
        try {
            authApi.register(email, password);
            user.setEmail(email);
            user.setPassword(password);
            registeredEmail = email;
        } catch (Exception e) {
            user.setEmail(email);
            user.setPassword(password);
            registeredEmail = email;
        }
    }

    @Тогда("отображается сообщение об ошибке регистрации")
    public void registrationErrorMessageIsDisplayed() {
        String error = getRegistrationPage().getErrorMessage();
        assertTrue(error != null && !error.isEmpty(),
                "Должно отображаться сообщение об ошибке регистрации");
    }
}
