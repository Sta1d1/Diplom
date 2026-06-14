package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Главная страница с объявлениями.
 */
public class HomePage extends BasePage {

    private final SelenideElement createAdButton = $(byXpath("//button[text()='Разместить объявление']"));

    public HomePage() {
        super("/");
    }

    public HomePage openHomePage() {
        openPage();
        return this;
    }
}
