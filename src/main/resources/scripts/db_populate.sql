USE wishlistdatabase;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
TRUNCATE TABLE wishlists;
TRUNCATE TABLE items;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO users (username, email, password)
VALUES
    ('Maiken', 'test@mail.dk', 'password123'),
    ('Emil', 'test2@mail.dk', 'password456');

INSERT INTO wishlists (title, user_id)
VALUES (
           'Christmas',
           1);

INSERT INTO wishlists (title, description, user_id)
VALUES (
           'Birthday',
           'Family only',
           1);

INSERT INTO items (title, wishlist_id)
VALUES (
           'New computer',
           2);


