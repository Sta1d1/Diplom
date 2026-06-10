package org.example.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {
    protected final String pageUrl;

    protected BasePage(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    /** Открыть страницу по базовому URL */
    public void openPage() {
        Selenide.open(pageUrl);
        $("body").shouldBe(visible);
    }

    public String getCurrentUrl() {
        return WebDriverRunner.url();
    }
}
