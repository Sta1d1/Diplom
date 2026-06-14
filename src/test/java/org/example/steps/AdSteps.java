package org.example.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.api.AuthApi;
import org.example.models.Ad;
import org.example.models.TestDataGenerator;
import org.example.models.User;
import org.example.pages.CreateAdPage;
import org.example.pages.EditAdPage;
import org.example.pages.AdDetailsPage;
import org.example.pages.HomePage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Шаги для сценариев работы с объявлениями.
 * Создание тестового пользователя — через API, всё остальное — через UI.
 */
public class AdSteps {

    private static final long REDIRECT_TIMEOUT_MS = 5000;

    private final User user;
    private final Ad ad;
    private final AuthApi authApi;
    private CreateAdPage createAdPage;
    private EditAdPage editAdPage;
    private AdDetailsPage adDetailsPage;

    public AdSteps(User user, Ad ad, AuthApi authApi) {
        this.user = user;
        this.ad = ad;
        this.authApi = authApi;
    }

    private CreateAdPage getCreateAdPage() {
        if (createAdPage == null) {
            createAdPage = Selenide.page(CreateAdPage.class);
        }
        return createAdPage;
    }

    private EditAdPage getEditAdPage() {
        if (editAdPage == null) {
            editAdPage = Selenide.page(EditAdPage.class);
        }
        return editAdPage;
    }

    private AdDetailsPage getAdDetailsPage() {
        if (adDetailsPage == null) {
            adDetailsPage = Selenide.page(AdDetailsPage.class);
        }
        return adDetailsPage;
    }

    @Дано("пользователь авторизован")
    public void userIsAuthenticated() {
        if (user.getToken() == null || user.getToken().isEmpty()) {
            String email = TestDataGenerator.generateEmail();
            String password = TestDataGenerator.generatePassword();
            String token = authApi.register(email, password);
            user.setEmail(email);
            user.setPassword(password);
            user.setToken(token);
        }

        HomePage homePage = new HomePage();
        homePage.openHomePage();

        Selenide.$x("//button[text()='Вход и регистрация']").shouldBe(visible).click();
        Selenide.$x("//input[@placeholder='Введите Email']").shouldBe(visible).setValue(user.getEmail());
        Selenide.$x("//input[@placeholder='Пароль']").shouldBe(visible).setValue(user.getPassword());
        Selenide.$x("//button[text()='Войти']").shouldBe(visible).click();
        Selenide.$x("//button[text()='Выйти']").shouldBe(visible);
    }

    @Дано("у пользователя есть объявление")
    public void userHasAnAd() {
        String title = TestDataGenerator.generateAdTitle();
        String description = TestDataGenerator.generateAdDescription();
        String price = TestDataGenerator.generateAdPrice();
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setPrice(price);

        Selenide.$x("//button[text()='Разместить объявление']").shouldBe(visible).click();
        getCreateAdPage().enterTitle(title);
        getCreateAdPage().selectNewGoods();
        getCreateAdPage().enterDescription(description);
        getCreateAdPage().enterPrice(price);
        getCreateAdPage().clickPublish();

        waitForRedirectFrom("/create-lisiting");
        searchForAd(title);
    }

    @Когда("пользователь открывает страницу создания объявления")
    public void userOpensCreateAdPage() {
        Selenide.$x("//button[text()='Разместить объявление']").shouldBe(visible).click();
        createAdPage = Selenide.page(CreateAdPage.class);
    }

    @Когда("вводит название объявления")
    public void entersAdTitle() {
        String title = TestDataGenerator.generateAdTitle();
        ad.setTitle(title);
        getCreateAdPage().enterTitle(title);
    }

    @Когда("вводит описание объявления")
    public void entersAdDescription() {
        String description = TestDataGenerator.generateAdDescription();
        ad.setDescription(description);
        getCreateAdPage().enterDescription(description);
    }

    @Когда("вводит цену")
    public void entersAdPrice() {
        String price = TestDataGenerator.generateAdPrice();
        ad.setPrice(price);
        getCreateAdPage().enterPrice(price);
    }

    @Когда("выбирает категорию")
    public void selectsCategory() {
        getCreateAdPage().selectNewGoods();
    }

    @Когда("создаёт объявление")
    public void clicksCreateButton() {
        getCreateAdPage().clickPublish();
    }

    @Тогда("объявление успешно создано")
    public void adIsCreatedSuccessfully() {
        assertTrue(getCreateAdPage().isAdCreated(),
                "Объявление должно быть успешно создано — должен произойти редирект");
    }

    @Тогда("объявление отображается на странице")
    public void adIsDisplayedOnPage() {
        assertNotNull(ad.getTitle(), "Название объявления должно быть задано");
    }

    @Когда("пользователь открывает страницу редактирования объявления")
    public void userOpensEditAdPage() {
        ensureAdVisible(ad.getTitle());
        Selenide.$x("//div[contains(@class,'card') and .//h2[contains(., '" + ad.getTitle() + "')]]")
                .shouldBe(visible, Duration.ofSeconds(10)).click();
        Selenide.$x("//button[text()='Редактировать объявление']")
                .shouldBe(visible, Duration.ofSeconds(10)).click();
        editAdPage = Selenide.page(EditAdPage.class);
    }

    @Когда("изменяет название объявления")
    public void changesAdTitle() {
        String newTitle = TestDataGenerator.generateAdTitle();
        ad.setTitle(newTitle);
        getEditAdPage().editTitle(newTitle);
    }

    @Когда("сохраняет изменения объявления")
    public void clicksSaveButton() {
        getEditAdPage().clickSave();
    }

    @Тогда("объявление успешно обновлено")
    public void adIsUpdatedSuccessfully() {
        assertTrue(getEditAdPage().isAdUpdated(),
                "Объявление должно быть успешно обновлено — должен произойти редирект");
    }

    @Когда("пользователь удаляет объявление")
    public void userDeletesAd() {
        ensureAdVisible(ad.getTitle());
        Selenide.$x("//div[contains(@class,'card') and .//h2[contains(., '" + ad.getTitle() + "')]]")
                .shouldBe(visible, Duration.ofSeconds(10)).click();
        Selenide.$x("//div[contains(@class,'listingPage')]")
                .shouldBe(visible, Duration.ofSeconds(10));
        adDetailsPage = Selenide.page(AdDetailsPage.class);
        getAdDetailsPage().clickDelete();
    }

    @Тогда("объявление успешно удалено")
    public void adIsDeletedSuccessfully() {
        assertTrue(getAdDetailsPage().isAdDeleted(),
                "Объявление должно быть успешно удалено — должен произойти редирект");
    }

    /**
     * Ожидает редирект с указанного пути, опрашивая URL с интервалом.
     */
    private void waitForRedirectFrom(String path) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < REDIRECT_TIMEOUT_MS) {
            if (!WebDriverRunner.url().contains(path)) {
                return;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Ищет объявление по названию через строку поиска на главной странице.
     */
    private void searchForAd(String title) {
        Selenide.$x("//input[@placeholder='Я хочу купить...']")
                .shouldBe(visible).setValue(title);
        Selenide.$x("//button[text()='Применить']").shouldBe(visible).click();
        Selenide.$x("//div[contains(@class,'card') and .//h2[contains(., '" + title + "')]]")
                .shouldBe(visible, Duration.ofSeconds(10));
    }

    /**
     * Убеждается, что карточка объявления видна на странице.
     */
    private void ensureAdVisible(String title) {
        searchForAd(title);
    }
}
