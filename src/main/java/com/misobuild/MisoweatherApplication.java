package com.misobuild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MisoweatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MisoweatherApplication.class, args);
    }

}
