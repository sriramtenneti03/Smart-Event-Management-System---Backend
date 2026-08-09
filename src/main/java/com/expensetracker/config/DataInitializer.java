package com.expensetracker.config;

import com.expensetracker.entity.Category;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (userRepository.count() > 0) {
            System.out.println("Data already initialized. Skipping...");
            return;
        }

        System.out.println("Initializing test data...");

        // Create test users
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john@example.com");
        user1.setPassword(passwordEncoder.encode("password123"));
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane@example.com");
        user2.setPassword(passwordEncoder.encode("password123"));
        user2 = userRepository.save(user2);

        // Create categories with random data
        List<Category> categories = Arrays.asList(
            createCategory("Food & Dining", "🍔", "#FF6384"),
            createCategory("Transportation", "🚗", "#36A2EB"),
            createCategory("Shopping", "🛍️", "#FFCE56"),
            createCategory("Entertainment", "🎬", "#4BC0C0"),
            createCategory("Bills & Utilities", "💡", "#9966FF"),
            createCategory("Healthcare", "🏥", "#FF9F40"),
            createCategory("Travel", "✈️", "#C9CBCF"),
            createCategory("Education", "📚", "#7BC225")
        );
        categories = categoryRepository.saveAll(categories);

        // Create random expenses for the last 6 months
        Random random = new Random();
        String[] descriptions = {
            "Grocery shopping", "Restaurant dinner", "Gas station", "Movie tickets",
            "Online shopping", "Electric bill", "Doctor visit", "Book purchase",
            "Concert tickets", "Uber ride", "Coffee shop", "Gym membership",
            "Internet bill", "Phone bill", "Clothing store", "Electronics purchase"
        };

        for (int i = 0; i < 50; i++) {
            Expense expense = new Expense();
            expense.setDescription(descriptions[random.nextInt(descriptions.length)]);
            expense.setAmount(BigDecimal.valueOf(10.0 + (random.nextDouble() * 200)));
            expense.setExpenseDate(LocalDate.now().minusDays(random.nextInt(180)));
            expense.setCategory(categories.get(random.nextInt(categories.size())));
            expense.setUser(user1);
            expense.setTags("sample");
            expenseRepository.save(expense);
        }

        // Add some expenses for user2 as well
        for (int i = 0; i < 20; i++) {
            Expense expense = new Expense();
            expense.setDescription(descriptions[random.nextInt(descriptions.length)]);
            expense.setAmount(BigDecimal.valueOf(10.0 + (random.nextDouble() * 150)));
            expense.setExpenseDate(LocalDate.now().minusDays(random.nextInt(180)));
            expense.setCategory(categories.get(random.nextInt(categories.size())));
            expense.setUser(user2);
            expense.setTags("sample");
            expenseRepository.save(expense);
        }

        System.out.println("Test data initialized successfully!");
        System.out.println("Created " + userRepository.count() + " users");
        System.out.println("Created " + categoryRepository.count() + " categories");
        System.out.println("Created " + expenseRepository.count() + " expenses");
    }

    private Category createCategory(String name, String icon, String color) {
        Category category = new Category();
        category.setName(name);
        category.setIcon(icon);
        category.setColor(color);
        return category;
    }
}
