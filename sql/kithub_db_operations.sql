use kithubdb;

ALTER TABLE customers
ALTER image SET DEFAULT '/var/www/html/images/user/default.png';

ALTER TABLE customers MODIFY is_admin BOOL;
ALTER TABLE author_id
ALTER is_admin SET DEFAULT 0;

ALTER TABLE cart_items
  ADD CONSTRAINT uq_cart_items UNIQUE(cart_id, book_id);
    
ALTER TABLE authors
  ADD CONSTRAINT uq_authors UNIQUE(first_name, last_name);
    
ALTER TABLE book_types
  ADD CONSTRAINT uq_book_types UNIQUE(book_type_name, category_id);

ALTER TABLE reviews
  ADD CONSTRAINT uq_review UNIQUE(customer_id, book_id);
            
INSERT INTO shippers (company_name) VALUES ("TNT");
INSERT INTO shippershippersshipperss (company_name) VALUES ("UPS");

ALTER TABLE order_details
ADD COLUMN quantity INT;

ALTER TABLE orders
ADD COLUMN total_order_price INT;

ALTER TABLE order_details ADD COLUMN id INT(11) PRIMARY KEY AUTO_INCREMENT;

ALTER TABLE orders
ADD COLUMN address VARCHAR(150);

ALTER TABLE discounts
  ADD CONSTRAINT uq_discount UNIQUE(book_id);

ALTER TABLE orders
ADD COLUMN is_confirmed TINYINT(1);

DELIMITER $$
CREATE TRIGGER register_log_trigger 
    AFTER INSERT ON customers
    FOR EACH ROW BEGIN
INSERT INTO user_logs (event_name, customer_id) VALUES("Kaydolma",  NEW.id);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER user_update_log_trigger 
    AFTER UPDATE ON customers
    FOR EACH ROW BEGIN
INSERT INTO user_logs (event_name, customer_id) VALUES("Bilgi Güncelleme",  NEW.id);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER review_log_trigger 
    AFTER INSERT ON reviews
    FOR EACH ROW BEGIN
INSERT INTO review_logs (review_id) VALUES(NEW.id);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER user_info_update_log_trigger 
    AFTER UPDATE ON customers
    FOR EACH ROW BEGIN
INSERT INTO user_update_logs (customer_id) VALUES(NEW.id);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER order_log_trigger 
    AFTER INSERT ON orders
    FOR EACH ROW BEGIN
INSERT INTO order_logs (order_id) VALUES(NEW.id);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER cart_add_log_trigger 
    AFTER INSERT ON cart_items
    FOR EACH ROW BEGIN
INSERT INTO cart_logs (event_name, customer_id, book_id, quantity) VALUES
					("Ekleme", 
                    (SELECT customer_id FROM carts c JOIN cart_items ci ON c.id = ci.cart_id WHERE ci.id = NEW.id), 
                    (SELECT book_id FROM cart_items WHERE id = NEW.id),
                    (SELECT quantity FROM cart_items WHERE id = NEW.id));
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER cart_del_log_trigger 
    BEFORE DELETE ON cart_items
    FOR EACH ROW BEGIN
INSERT INTO cart_logs (event_name, customer_id, book_id, quantity) VALUES
					("Silme", 
                    (SELECT customer_id FROM carts c JOIN cart_items ci ON c.id = ci.cart_id WHERE ci.id = OLD.id), 
                    (SELECT book_id FROM cart_items WHERE id = OLD.id), 
                    (SELECT quantity FROM cart_items WHERE id = OLD.id));
END$$
DELIMITER ;