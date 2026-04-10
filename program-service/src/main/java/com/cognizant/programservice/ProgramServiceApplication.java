package com.cognizant.programservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cognizant")
@EnableFeignClients
public class ProgramServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgramServiceApplication.class, args);
    }

}
