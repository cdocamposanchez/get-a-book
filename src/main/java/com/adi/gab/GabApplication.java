package com.adi.gab;

import com.adi.gab.infrastructure.config.DotenvLoader;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class GabApplication {

    public static void main(String[] args) {
        DotenvLoader.loadEnv();

        SpringApplication.run(GabApplication.class, args);
    }

}
