DROP DATABASE IF EXISTS wishlistdatabase;
CREATE DATABASE wishlistdatabase;
USE wishlistdatabase;

-- Table: Users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Table: Wishlists
CREATE TABLE wishlists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Table: Items
CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    link VARCHAR(255),
    quantity INT DEFAULT 1,
    image_url VARCHAR(255),
    wishlist_id INT NOT NULL,
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

