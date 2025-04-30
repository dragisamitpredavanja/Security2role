package com.sbs.ald.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure()
            .directory("src/main/resources") // ili ostavite default ako je u root-u
            .load();
    }
}

