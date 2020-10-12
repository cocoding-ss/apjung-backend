package me.apjung.backend.property.AppProps;

public enum AppEnv {
    LOCAL("local"),
    TEST("test"),
    DEV("dev"),
    PROD("prod");

    private final String env;

    AppEnv(String env) {
        this.env = env;
    }
}
