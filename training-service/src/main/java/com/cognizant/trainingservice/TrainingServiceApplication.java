package com.cognizant.trainingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cognizant")
@EnableFeignClients
public class TrainingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingServiceApplication.class, args);
    }

}
