use kithubdb;

SET foreign_key_checks = 0;

DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS book_types;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS shippers;
DROP TABLE IF EXISTS customers;

SET foreign_key_checks = 1;

CREATE TABLE customers (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(30),
    last_name VARCHAR(20),
    phone CHAR(10),
    e_mail VARCHAR(40) NOT NULL,
    is_admin BOOLEAN,
    user_password CHAR(15) NOT NULL,
    address VARCHAR(200),
    PRIMARY KEY (id)
);

CREATE TABLE shippers (
    id INT AUTO_INCREMENT,
    company_name VARCHAR(30),
    PRIMARY KEY (id)
);

CREATE TABLE authors (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(30),
    last_name VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE categories (
    id INT AUTO_INCREMENT,
    category_name VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE book_types (
    id INT AUTO_INCREMENT,
    book_type_name VARCHAR(20),
    category_id INT,
    PRIMARY KEY (id),
	FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE CASCADE
);

CREATE TABLE books (
    id INT AUTO_INCREMENT,
    ISBN CHAR(13) NOT NULL,
    title VARCHAR(40) NOT NULL,
    author_id INT,
    stock_quantity INT,
    category_id INT,
    book_type_id INT,
    price INT,
    sales INT DEFAULT 0,
    rating FLOAT DEFAULT 0,
    number_of_people_rated INT DEFAULT 0,
    summary VARCHAR(500),
    PRIMARY KEY (id),
    FOREIGN KEY (author_id)
        REFERENCES authors (id)
        ON DELETE CASCADE,
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE CASCADE,
    FOREIGN KEY (book_type_id)
        REFERENCES book_types (id)
        ON DELETE CASCADE
);

CREATE TABLE discounts (
    id INT AUTO_INCREMENT,
    discount_value INT NOT NULL,
    book_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (book_id)
        REFERENCES books (id)
        ON DELETE CASCADE
);

CREATE TABLE carts (
    id INT AUTO_INCREMENT,
    customer_id INT,
    checked_out BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customers (id)
        ON DELETE CASCADE
);

CREATE TABLE cart_items (
    id INT AUTO_INCREMENT,
    cart_id INT,
    book_id INT,
    quantity INT,
    PRIMARY KEY (id),
    FOREIGN KEY (book_id)
        REFERENCES books (id)
        ON DELETE CASCADE,
    FOREIGN KEY (cart_id)
        REFERENCES carts (id)
        ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT,
    customer_id INT,
    shipper_id INT,
    order_time DATETIME,
    discount_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customers (id)
        ON DELETE CASCADE,
    FOREIGN KEY (shipper_id)
        REFERENCES shippers (id),
    FOREIGN KEY (discount_id)
        REFERENCES discounts (id)
);

CREATE TABLE order_details (
    book_id INT,
    order_id INT,
    total_price INT,
    FOREIGN KEY (book_id)
        REFERENCES books (id)
        ON DELETE CASCADE,
    FOREIGN KEY (order_id)
        REFERENCES orders (id)
        ON DELETE CASCADE
);

CREATE TABLE reviews (
    id INT AUTO_INCREMENT,
    book_id INT,
    customer_id INT,
    review_text VARCHAR(900),
    review_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    rating TINYINT DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customers (id)
        ON DELETE CASCADE,
    FOREIGN KEY (book_id)
        REFERENCES books (id)
        ON DELETE CASCADE
);

CREATE TABLE user_logs (
    id INT AUTO_INCREMENT,
    event_name VARCHAR(10),
    customer_id INT,
    action_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customers (id)
);

CREATE TABLE cart_logs (
    id INT AUTO_INCREMENT,
    event_name VARCHAR(10),
    customer_id INT,
    book_id INT,
    quantity INT,
    action_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (book_id)
        REFERENCES books (id),
    FOREIGN KEY (customer_id)
        REFERENCES customers (id)
);

CREATE TABLE order_logs (
    id INT AUTO_INCREMENT,
    order_id INT,
    action_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id)
        REFERENCES orders (id)
);

CREATE TABLE review_logs (
    id INT AUTO_INCREMENT,
    review_id INT,
    action_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (review_id)
        REFERENCES reviews (id)
);

CREATE TABLE user_update_logs (
    id INT AUTO_INCREMENT,
    customer_id INT,
    action_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customers (id)
);