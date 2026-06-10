package org.example.pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AdCard {

    private final SelenideElement titleLink = $(".ad-card a, .card-title a, [class*='ad'] a, h3 a");
    private final SelenideElement priceText = $(".ad-price, .card-price, [class*='price']");

    public String getTitle() {
        return titleLink.shouldBe(visible).getText();
    }

    public String getPrice() {
        return priceText.shouldBe(visible).getText();
    }

    public void click() {
        titleLink.shouldBe(visible).click();
    }
}
