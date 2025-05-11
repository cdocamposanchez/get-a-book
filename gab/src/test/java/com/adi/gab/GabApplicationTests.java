package com.adi.gab;

import com.adi.gab.application.DotenvInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class GabApplicationTests {

    @Test
    void contextLoads() {
    }

}
