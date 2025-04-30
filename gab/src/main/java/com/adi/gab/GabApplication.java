package com.adi.gab;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GabApplication {

    public static void main(String[] args) {
        Dotenv.load();
        SpringApplication.run(GabApplication.class, args);
    }

}
