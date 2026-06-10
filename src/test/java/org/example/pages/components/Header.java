package org.example.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Компонент шапки сайта — логотип, кнопка «Выйти».
 */
public class Header {

    private final SelenideElement logoutButton = $(byXpath("//button[text()='Выйти']"));

    public Header clickLogout() {
        logoutButton.shouldBe(visible).click();
        return this;
    }

    public boolean isLoggedIn() {
        return logoutButton.exists();
    }
}
