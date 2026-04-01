-- ===========================
-- Players Table
-- ===========================
INSERT INTO users (name, email, username, password, role) VALUES
('Admin User', 'admin@gmail.com', 'admin', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'ADMIN');
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

INSERT INTO users (name, email, username, password, role) VALUES 
('Samsun Verma', 'n01726448@huber.ca', 'samsun', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('James Anderson', 'james.anderson@gmail.com', 'james', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Michael Chen', 'michael.chen@gmail.com', 'michael', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('David Johnson', 'david.johnson@gmail.com', 'david', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Jennifer Smith', 'jennifer.smith@gmail.com', 'jennifer', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Sarah Williams', 'sarah.williams@gmail.com', 'sarah', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Robert Brown', 'robert.brown@gmail.com', 'robert', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Emily Davis', 'emily.davis@gmail.com', 'emily', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Christopher Wilson', 'christopher.wilson@gmail.com', 'christopher', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Amanda Martinez', 'amanda.martinez@gmail.com', 'amanda', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER'),
('Ophelia Shields', 'ophelia.shields@gmail.com', 'ophelia', '$2a$10$7QX.5yq0lI1.ZoJ8X9z7EeYx9/ogFt6aJ9EsmXh8kU1y6dGxDkT1y', 'PLAYER');