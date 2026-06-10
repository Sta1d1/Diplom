package org.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

/**
 * Главная страница с объявлениями.
 */
public class HomePage extends BasePage {

    private final SelenideElement createAdButton = $(byXpath("//button[text()='Разместить объявление']"));
    private final SelenideElement authButton = $(byXpath("//button[text()='Вход и регистрация']"));
    private final ElementsCollection adCards = $$("[class*='card'], [class*='ad'], [class*='listing']");

    public HomePage() {
        super("/");
    }

    public HomePage openHomePage() {
        openPage();
        return this;
    }

    public boolean isPageLoaded() {
        return $("body").is(visible);
    }

    public boolean isAuthButtonVisible() {
        return authButton.exists();
    }

    public HomePage clickCreateAd() {
        createAdButton.shouldBe(visible).click();
        return this;
    }
}
