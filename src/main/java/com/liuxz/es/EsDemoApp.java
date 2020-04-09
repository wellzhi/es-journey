package com.liuxz.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Administrator
 */
@SpringBootApplication(scanBasePackages = "com.liuxz")
public class EsDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(EsDemoApp.class, args);
    }

}
