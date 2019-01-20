package com.glovoapp.backender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
@SpringBootApplication
public class GlovoApp {

    public static void main(String[] args) {
        SpringApplication.run(GlovoApp.class);
    }
}
