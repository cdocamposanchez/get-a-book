package com.adi.gab;

import com.adi.gab.infrastructure.config.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GabApplication {

    public static void main(String[] args) {
        DotenvLoader.loadEnv();

        SpringApplication.run(GabApplication.class, args);
    }

}
