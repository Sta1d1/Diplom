package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница редактирования объявления.
 * URL: /edit-listing/{id}
 */
public class EditAdPage extends BasePage {

    private static final String PAGE_URL_PATH = "/edit-listing";

    private final SelenideElement titleInput = $(byXpath("//input[@placeholder='Название']"));
    private final SelenideElement saveButton = $(byXpath("//button[text()='Сохранить изменения']"));

    public EditAdPage() {
        super(PAGE_URL_PATH);
    }

    public EditAdPage editTitle(String title) {
        titleInput.shouldBe(visible).clear();
        titleInput.setValue(title);
        return this;
    }

    public EditAdPage clickSave() {
        saveButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяет, что редактирование прошло успешно — произошёл редирект с /edit-listing.
     */
    public boolean isAdUpdated() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return !getCurrentUrl().contains(PAGE_URL_PATH);
    }
}
