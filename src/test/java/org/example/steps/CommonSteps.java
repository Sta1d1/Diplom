package org.example.steps;

import io.cucumber.java.ru.Когда;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

/**
 * Общие шаги, используемые в разных сценариях.
 */
public class CommonSteps {

    @Когда("нажимает кнопку {string}")
    public void clicksButton(String buttonText) {
        $(byXpath("//button[text()='" + buttonText + "']")).shouldBe(visible).click();
    }
}
