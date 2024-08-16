-- -----------------------------------------------------
-- users
-- -----------------------------------------------------
INSERT INTO ROLE(id, NAME)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO ROLE(id, NAME)
VALUES (2, 'ROLE_USER');

INSERT INTO USER(id,EMAIL,FIRST_NAME,ENABLED,PASSWORD,IS_SOCIAL)
VALUES (1, 'admin@email.com','Admin',1,'$2a$04$Buxup01QZW.Ytq.1PuVJCuHPynjLA5p19gnYgPX.SKYSp5xe0pQF6',0);

INSERT INTO users_ROLES(USER_ID,ROLE_ID)
VALUES (1, 1);

SET
@id=0;

-- -----------------------------------------------------
-- Categories
-- -----------------------------------------------------
INSERT INTO category(id, NAME)
VALUES (1, 'Books');
INSERT INTO category(id, NAME)
VALUES (2, 'Coffee Mugs');
INSERT INTO category(id, NAME)
VALUES (3, 'Mouse Pads');
INSERT INTO category(id, NAME)
VALUES (4, 'Luggage Tags');

SET
@id=0;
-- -----------------------------------------------------
-- Books
-- -----------------------------------------------------

set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1000', 'Crash Course in Python',
        'Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1000.png', 1, 100, 14.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1001', 'Become a Guru in JavaScript',
        'Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1001.png', 1, 100, 20.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1002', 'Exploring Vue.js',
        'Learn Vue.js at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1002.png', 1, 100, 14.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1003', 'Advanced Techniques in Big Data',
        'Learn Big Data at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1003.png', 1, 100, 13.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1004', 'Crash Course in Big Data',
        'Learn Big Data at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1004.png', 1, 100, 18.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1005', 'JavaScript Cookbook',
        'Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1005.png', 1, 100, 23.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1006', 'Beginners Guide to SQL',
        'Learn SQL at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1006.png', 1, 100, 14.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1007', 'Advanced Techniques in JavaScript',
        'Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1007.png', 1, 100, 16.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1008', 'Introduction to Spring Boot',
        'Learn Spring Boot at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1008.png', 1, 100, 25.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1009', 'Become a Guru in React.js',
        'Learn React.js at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1009.png', 1, 100, 23.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1010', 'Beginners Guide to Data Science',
        'Learn Data Science at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1010.png', 1, 100, 24.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1011', 'Advanced Techniques in Java',
        'Learn Java at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1011.png', 1, 100, 19.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1012', 'Exploring DevOps',
        'Learn DevOps at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1012.png', 1, 100, 24.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1013', 'The Expert Guide to SQL',
        'Learn SQL at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1013.png', 1, 100, 19.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1014', 'Introduction to SQL',
        'Learn SQL at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1014.png', 1, 100, 22.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1015', 'The Expert Guide to JavaScript',
        'Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1015.png', 1, 100, 22.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1016', 'Exploring React.js',
        'Learn React.js at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1016.png', 1, 100, 27.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1017', 'Advanced Techniques in React.js',
        'Learn React.js at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1017.png', 1, 100, 13.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1018', 'Introduction to C#',
        'Learn C# at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1018.png', 1, 100, 26.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1019', 'Crash Course in JavaScript',
        'Learn JavaScript at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1019.png', 1, 100, 13.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1020', 'Introduction to Machine Learning',
        'Learn Machine Learning at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1020.png', 1, 100, 19.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1021', 'Become a Guru in Java',
        'Learn Java at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1021.png', 1, 100, 18.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1022', 'Introduction to Python',
        'Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1022.png', 1, 100, 26.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1023', 'Advanced Techniques in C#',
        'Learn C# at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1023.png', 1, 100, 22.99, 1, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'BOOK-TECH-1024', 'The Expert Guide to Machine Learning',
        'Learn Machine Learning at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!',
        'book-luv2code-1024.png', 1, 100, 16.99, 1, NOW());

-- -----------------------------------------------------
-- Coffee Mugs
-- -----------------------------------------------------
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1000', 'Coffee Mug - Express',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1000.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1001', 'Coffee Mug - Cherokee',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1001.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1002', 'Coffee Mug - Sweeper',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1002.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1003', 'Coffee Mug - Aspire',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1003.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1004', 'Coffee Mug - Dorian',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1004.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1005', 'Coffee Mug - Columbia',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1005.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1006', 'Coffee Mug - Worthing',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1006.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1007', 'Coffee Mug - Oak Cliff',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1007.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1008', 'Coffee Mug - Tachyon',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1008.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1009', 'Coffee Mug - Pan',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1009.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1010', 'Coffee Mug - Phase',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1010.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1011', 'Coffee Mug - Falling',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1011.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1012', 'Coffee Mug - Wispy',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1012.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1013', 'Coffee Mug - Arlington',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1013.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1014', 'Coffee Mug - Gazing',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1014.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1015', 'Coffee Mug - Azura',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1015.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1016', 'Coffee Mug - Quantum Leap',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1016.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1017', 'Coffee Mug - Light Years',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1017.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1018', 'Coffee Mug - Taylor',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1018.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1019', 'Coffee Mug - Gracia',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1019.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1020', 'Coffee Mug - Relax',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1020.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1021', 'Coffee Mug - Windermere',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1021.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1022', 'Coffee Mug - Prancer',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1022.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1023', 'Coffee Mug - Recursion',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1023.png', 1, 100, 18.99, 2, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'COFFEEMUG-1024', 'Coffee Mug - Treasure',
        'Do you love mathematics? If so, then you need this elegant coffee mug with an amazing fractal design. You dont have to worry about boring coffee mugs anymore. This coffee mug will be the topic of conversation in the office, guaranteed! Buy it now!',
        'coffeemug-luv2code-1024.png', 1, 100, 18.99, 2, NOW());
-- -----------------------------------------------------
-- Mouse Pads
-- -----------------------------------------------------
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1000', 'Mouse Pad - Express',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1000.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1001', 'Mouse Pad - Cherokee',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1001.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1002', 'Mouse Pad - Sweeper',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1002.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1003', 'Mouse Pad - Aspire',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1003.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1004', 'Mouse Pad - Dorian',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1004.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1005', 'Mouse Pad - Columbia',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1005.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1006', 'Mouse Pad - Worthing',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1006.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1007', 'Mouse Pad - Oak Cliff',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1007.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1008', 'Mouse Pad - Tachyon',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1008.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1009', 'Mouse Pad - Pan',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1009.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1010', 'Mouse Pad - Phase',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1010.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1011', 'Mouse Pad - Falling',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1011.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1012', 'Mouse Pad - Wispy',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1012.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1013', 'Mouse Pad - Arlington',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1013.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1014', 'Mouse Pad - Gazing',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1014.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1015', 'Mouse Pad - Azura',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1015.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1016', 'Mouse Pad - Quantum Leap',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1016.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1017', 'Mouse Pad - Light Years',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1017.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1018', 'Mouse Pad - Taylor',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1018.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1019', 'Mouse Pad - Gracia',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1019.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1020', 'Mouse Pad - Relax',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1020.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1021', 'Mouse Pad - Windermere',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1021.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1022', 'Mouse Pad - Prancer',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1022.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1023', 'Mouse Pad - Recursion',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1023.png', 1, 100, 17.99, 3, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'MOUSEPAD-1024', 'Mouse Pad - Treasure',
        'Fractal images are amazing! You can now own a mouse pad with a unique and amazing fractal. The mouse pad is made of a durable and smooth material. Your mouse will easily glide across the mouse pad. This mouse pad will brighten your workspace. Buy it now!',
        'mousepad-luv2code-1024.png', 1, 100, 17.99, 3, NOW());
-- -----------------------------------------------------
-- Luggage Tags
-- -----------------------------------------------------
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1000', 'Luggage Tag - Cherish',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1000.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1001', 'Luggage Tag - Adventure',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1001.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1002', 'Luggage Tag - Skyline',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1002.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1003', 'Luggage Tag - River',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1003.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1004', 'Luggage Tag - Trail Steps',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1004.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1005', 'Luggage Tag - Blooming',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1005.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1006', 'Luggage Tag - Park',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1006.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1007', 'Luggage Tag - Beauty',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1007.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1008', 'Luggage Tag - Water Fall',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1008.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1009', 'Luggage Tag - Trail',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1009.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1010', 'Luggage Tag - Skyscraper',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1010.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1011', 'Luggage Tag - Leaf',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1011.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1012', 'Luggage Tag - Jungle',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1012.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1013', 'Luggage Tag - Shoreline',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1013.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1014', 'Luggage Tag - Blossom',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1014.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1015', 'Luggage Tag - Lock',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1015.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1016', 'Luggage Tag - Cafe',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1016.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1017', 'Luggage Tag - Darling',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1017.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1018', 'Luggage Tag - Full Stack',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1018.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1019', 'Luggage Tag - Courtyard',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1019.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1020', 'Luggage Tag - Coaster',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1020.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1021', 'Luggage Tag - Bridge',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1021.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1022', 'Luggage Tag - Sunset',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1022.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1023', 'Luggage Tag - Flames',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1023.png', 1, 100, 16.99, 4, NOW());
set
@id=@id+1;
INSERT INTO PRODUCT (id, SKU, NAME, DESCRIPTION, IMAGE, ACTIVE, UNITS_IN_STOCK, UNIT_PRICE, CATEGORY_ID, DATE_CREATED)
VALUES (@id, 'LUGGAGETAG-1024', 'Luggage Tag - Countryside',
        'This luggage tag will help you identify your luggage. The luggage tag is very unique and it will stand out from the crowd. The luggage tag is created out of a rugged and durable plastic. Buy this luggage tag now to make it easy to identify your luggage!',
        'luggagetag-luv2code-1024.png', 1, 100, 16.99, 4, NOW());
