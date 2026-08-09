-- V1__Initial_schema.sql
-- Create users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create categories table
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    color VARCHAR(7) NOT NULL,
    icon VARCHAR(50),
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_category_name (user_id, name),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create expenses table
CREATE TABLE expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    expense_date DATE NOT NULL,
    tags VARCHAR(255),
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    is_recurring BOOLEAN DEFAULT FALSE,
    recurring_frequency VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
    INDEX idx_user_id (user_id),
    INDEX idx_category_id (category_id),
    INDEX idx_expense_date (expense_date),
    INDEX idx_user_date (user_id, expense_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create expense_splits table
CREATE TABLE expense_splits (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    expense_id BIGINT,
    participant_name VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    paid_by BOOLEAN NOT NULL DEFAULT FALSE,
    settled BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE SET NULL,
    INDEX idx_expense_id (expense_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create shared_expenses table
CREATE TABLE shared_expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    total_amount DECIMAL(19, 2) NOT NULL,
    paid_by_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_settled BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (paid_by_user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_paid_by_user_id (paid_by_user_id),
    INDEX idx_is_settled (is_settled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
