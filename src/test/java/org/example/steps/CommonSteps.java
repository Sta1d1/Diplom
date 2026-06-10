package org.example.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.config.Config;
import org.example.models.User;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Общие шаги, используемые в разных сценариях.
 * PicoContainer инжектит User для доступа к тестовым данным.
 */
public class CommonSteps {

    private final User user;

    public CommonSteps(User user) {
        this.user = user;
    }

    @Дано("пользователь на главной странице")
    public void userOnMainPage() {
        Selenide.open(Config.getBaseUrl());
    }

    @Когда("нажимает кнопку {string}")
    public void clicksButton(String buttonText) {
        $(byXpath("//button[text()='" + buttonText + "']")).shouldBe(visible).click();
    }

    @Тогда("отображается страница {string}")
    public void pageIsDisplayed(String pageName) {
        $("body").shouldBe(visible);
    }
}
