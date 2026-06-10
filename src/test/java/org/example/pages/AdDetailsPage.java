package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Страница объявления (детали просмотра).
 * URL: /listing/{id}
 */
public class AdDetailsPage extends BasePage {

    private static final String PAGE_URL_PATH = "/listing";

    private final SelenideElement deleteButton = $(byXpath("//button[text()='Удалить']"));

    public AdDetailsPage() {
        super(PAGE_URL_PATH);
    }

    /**
     * Кликает «Удалить».
     * В данном приложении кнопка «Удалить» удаляет объявление напрямую,
     * без модального окна подтверждения — происходит редирект на главную.
     */
    public AdDetailsPage clickDelete() {
        deleteButton.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяет, что удаление прошло успешно — произошёл редирект с /listing/.
     */
    public boolean isAdDeleted() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return !getCurrentUrl().contains(PAGE_URL_PATH + "/");
    }
}
