package org.example.models;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {

    private TestDataGenerator() {
    }

    public static String generateEmail() {
        return "test-" + UUID.randomUUID() + "@test.com";
    }

    public static String generatePassword() {
        return "TestPass123!";
    }

    public static String generateAdTitle() {
        return "Объявление-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateAdDescription() {
        return "Описание объявления для тестирования";
    }

    public static String generateAdPrice() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100, 10000));
    }
}
