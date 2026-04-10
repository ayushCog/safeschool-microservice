package com.cognizant.resolutionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cognizant")
@EnableFeignClients
public class ResolutionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResolutionServiceApplication.class, args);
    }

}
