package com.studentresult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Student Result Management System.
 *
 * @SpringBootApplication combines:
 *   - @Configuration       : marks this as a config class
 *   - @EnableAutoConfiguration : Spring Boot auto-configures beans
 *   - @ComponentScan       : scans all classes in this package for Spring components
 */
@SpringBootApplication
public class StudentResultSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentResultSystemApplication.class, args);
        System.out.println("\n✅ Student Result Management System is running!");
        System.out.println("📌 Base URL: http://localhost:8080/api");
        System.out.println("📌 Test with Postman using the endpoints in README.md\n");
    }
}
