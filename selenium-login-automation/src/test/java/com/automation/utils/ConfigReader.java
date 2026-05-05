package com.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* Lê configurações do arquivo test.properties */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("test.properties")) {

            if (input == null) {
                throw new RuntimeException("Arquivo test.properties não encontrado no classpath.");
            }
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar test.properties", e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Propriedade não encontrada: " + key);
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    // Atalhos semânticos
    public static String baseUrl()          { return get("base.url"); }
    public static String validUsername()    { return get("valid.username"); }
    public static String validPassword()    { return get("valid.password"); }
    public static int    implicitWait()     { return getInt("implicit.wait"); }
    public static int    explicitWait()     { return getInt("explicit.wait"); }
}
