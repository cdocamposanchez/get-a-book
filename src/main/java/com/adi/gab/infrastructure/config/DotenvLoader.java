package com.adi.gab.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class DotenvLoader {

    private DotenvLoader() {}

    public static void loadEnv() {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("DB_URL", Objects.requireNonNull(dotenv.get("DB_URL")));
        System.setProperty("DB_USERNAME", Objects.requireNonNull(dotenv.get("DB_USERNAME")));
        System.setProperty("DB_PASSWORD", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
        System.setProperty("JWT_SECRET", Objects.requireNonNull(dotenv.get("JWT_SECRET")));
        System.setProperty("JWT_EXPIRATION", Objects.requireNonNull(dotenv.get("JWT_EXPIRATION")));

        System.setProperty("STRIPE_SECRET_KEY", Objects.requireNonNull(dotenv.get("STRIPE_SECRET_KEY")));
        System.setProperty("STRIPE_FRONTEND_SUCCESS", Objects.requireNonNull(dotenv.get("STRIPE_FRONTEND_SUCCESS")));
        System.setProperty("STRIPE_FRONTEND_CANCEL", Objects.requireNonNull(dotenv.get("STRIPE_FRONTEND_CANCEL")));
    }
}
