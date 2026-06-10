package org.example.pages.components;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NavigationMenu {

    private final ElementsCollection categoryLinks = $$(".nav a, .menu a, [class*='nav'] a");

    public NavigationMenu selectCategory(String categoryName) {
        $(byText(categoryName)).click();
        return this;
    }
}
