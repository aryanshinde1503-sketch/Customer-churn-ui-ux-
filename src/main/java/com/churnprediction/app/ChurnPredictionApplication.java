package com.churnprediction.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Customer Churn Prediction System.
 * A professional analytics platform for telecommunications customer churn management.
 */
@SpringBootApplication
public class ChurnPredictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChurnPredictionApplication.class, args);
        System.out.println("""
                
                ╔══════════════════════════════════════════════════════════════╗
                ║        Customer Churn Prediction System - v1.0.0            ║
                ║        Spring Boot 3 | Java 21 | H2 | Thymeleaf            ║
                ╠══════════════════════════════════════════════════════════════╣
                ║  Application  : http://localhost:8080                       ║
                ║  H2 Console   : http://localhost:8080/h2-console            ║
                ║  Dashboard    : http://localhost:8080/dashboard             ║
                ╚══════════════════════════════════════════════════════════════╝
                """);
    }
}
