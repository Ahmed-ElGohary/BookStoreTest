package com.bookstore.api.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try {
            java.io.InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");
            if (inputStream == null) {
                System.out.println("config.properties not found in classpath");
                throw new IOException("config.properties not found in classpath");
            }
            properties.load(inputStream);
            System.out.println("config.properties loaded successfully");
        } catch (IOException e) {
            System.out.println("Error loading config.properties: " + e.getMessage());
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}
