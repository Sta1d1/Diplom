package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница создания объявления.
 * URL: /create-lisiting (опечатка на сайте)
 */
public class CreateAdPage extends BasePage {

    private static final String PAGE_URL_PATH = "/create-lisiting";

    private final SelenideElement titleInput = $(byXpath("//input[@placeholder='Название']"));
    private final SelenideElement newGoodsRadio = $(byXpath("//label[text()='Новый']/../div"));
    private final SelenideElement descriptionInput = $(byXpath("//textarea[@placeholder='Описание товара']"));
    private final SelenideElement priceInput = $(byXpath("//input[@placeholder='Стоимость']"));
    private final SelenideElement publishButton = $(byXpath("//button[text()='Опубликовать']"));

    public CreateAdPage() {
        super(PAGE_URL_PATH);
    }

    public CreateAdPage enterTitle(String title) {
        titleInput.shouldBe(visible).setValue(title);
        return this;
    }

    public CreateAdPage selectNewGoods() {
        newGoodsRadio.shouldBe(visible).click();
        return this;
    }

    public CreateAdPage enterDescription(String description) {
        descriptionInput.shouldBe(visible).setValue(description);
        return this;
    }

    public CreateAdPage enterPrice(String price) {
        priceInput.shouldBe(visible).setValue(price);
        return this;
    }

    public CreateAdPage clickPublish() {
        publishButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяет, что создание объявления прошло успешно — произошёл редирект с /create-lisiting.
     */
    public boolean isAdCreated() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return !getCurrentUrl().contains(PAGE_URL_PATH);
    }
}
