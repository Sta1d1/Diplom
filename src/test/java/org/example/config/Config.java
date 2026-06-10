package org.example.config;

public class Config {
    private static final String DEFAULT_BASE_URL = "https://qa-desk.education-services.ru/";
    private static final String DEFAULT_BROWSER = "chrome";
    private static final String DEFAULT_TIMEOUT = "10000";
    private static final String DEFAULT_HEADLESS = "false";
    private static final String DEFAULT_API_BASE_URL = "https://qa-desk.education-services.ru/api/";

    public static String getBaseUrl() {
        return System.getProperty("base.url", DEFAULT_BASE_URL);
    }

    public static String getBrowser() {
        return System.getProperty("browser", DEFAULT_BROWSER);
    }

    public static long getTimeout() {
        return Long.parseLong(System.getProperty("timeout", DEFAULT_TIMEOUT));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", DEFAULT_HEADLESS));
    }

    public static String getApiBaseUrl() {
        return System.getProperty("api.base.url", DEFAULT_API_BASE_URL);
    }
}
