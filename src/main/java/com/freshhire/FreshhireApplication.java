package com.freshhire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FreshhireApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshhireApplication.class, args);
    }
}
