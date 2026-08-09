package com.expensetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Smart Expense Tracker Application
 * A comprehensive expense tracking and financial insights platform
 */
@SpringBootApplication
public class SmartExpenseTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartExpenseTrackerApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
