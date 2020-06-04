package com.example.redisevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RediseventApplication {


    public static void main(String[] args) {
        SpringApplication.run(RediseventApplication.class, args);
    }

}
