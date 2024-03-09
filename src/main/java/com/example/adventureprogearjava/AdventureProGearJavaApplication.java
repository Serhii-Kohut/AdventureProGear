package com.example.adventureprogearjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AdventureProGearJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdventureProGearJavaApplication.class, args);
    }

}
