CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2),
    categories VARCHAR(255),
    viewCount INT DEFAULT 0,
    purchaseCount INT DEFAULT 0,
    averageRating FLOAT DEFAULT 0.0
);
