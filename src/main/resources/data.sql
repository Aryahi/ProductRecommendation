-- SQL Insert Statement for Products

INSERT INTO products (name, description, price, categories, view_count, purchase_count, average_rating) VALUES
('Smartphone Pro Max', 'Advanced smartphone with 5G and quad-camera system', 999.99, 'Electronics,Mobile', 2500, 450, 4.7),
('Wireless Noise-Canceling Earbuds', 'Premium earbuds with active noise cancellation and long battery life', 199.50, 'Electronics,Audio', 1800, 320, 4.6),
('Gaming Desktop PC', 'High-performance gaming computer with latest Intel processor and NVIDIA RTX 4080', 2499.99, 'Electronics,Computers', 1200, 180, 4.9),
('Ergonomic Office Chair', 'Adjustable ergonomic chair with lumbar support and breathable mesh', 299.99, 'Furniture,Office', 750, 110, 4.5),
('Smart Home Security Camera', 'WiFi-enabled security camera with 4K resolution and night vision', 149.99, 'Home Appliances,Security', 1600, 240, 4.4),
('Professional Blender', 'High-speed blender with multiple speed settings and robust motor', 179.99, 'Kitchen Appliances,Cooking', 900, 160, 4.6),
('Mountain Bike', 'Lightweight aluminum frame mountain bike with 21-speed transmission', 599.99, 'Sports,Bicycles', 1100, 200, 4.7),
('Bluetooth Speaker', 'Waterproof portable speaker with 360-degree sound', 89.99, 'Electronics,Audio', 2000, 380, 4.5),
('Fitness Smartwatch', 'Advanced fitness tracker with heart rate monitor and GPS', 249.99, 'Electronics,Fitness', 1700, 290, 4.8),
('Robot Vacuum Cleaner', 'Smart robotic vacuum with app control and mapping technology', 349.99, 'Home Appliances,Cleaning', 1300, 220, 4.3),
('Wireless Charging Pad', 'Fast-charging wireless pad compatible with multiple devices', 39.99, 'Electronics,Accessories', 1500, 260, 4.6),
('Professional Camera', 'Full-frame mirrorless camera with 4K video capabilities', 1999.99, 'Electronics,Cameras', 800, 90, 4.9),
('Electric Kettle', 'Stainless steel electric kettle with temperature control', 79.99, 'Kitchen Appliances,Cooking', 1000, 180, 4.5),
('Running Shoes', 'Lightweight running shoes with advanced cushioning technology', 129.99, 'Sports,Footwear', 2200, 420, 4.7),
('Noise-Canceling Headphones', 'Over-ear headphones with premium sound quality', 249.50, 'Electronics,Audio', 1600, 280, 4.8);

-- Insert Users
INSERT INTO app_users (id, username, email, preferences) VALUES
(1, 'john_doe', 'john.doe@example.com', 'Electronics,Computers'),
(2, 'jane_smith', 'jane.smith@example.com', 'Home Appliances,Kitchen'),
(3, 'michael_brown', 'michael.brown@example.com', 'Sports,Footwear'),
(4, 'emily_johnson', 'emily.johnson@example.com', 'Electronics,Smartwatch'),
(5, 'david_wilson', 'david.wilson@example.com', 'Electronics,Audio');

-- Insert Viewed Products
INSERT INTO user_viewed_products (user_id, product_id) VALUES
(1, 1), (1, 3), (1, 5), 
(2, 6), (2, 8), 
(3, 4), (3, 9), 
(4, 5), (4, 7), 
(5, 2), (5, 10);

-- Insert Purchased Products
INSERT INTO user_purchased_products (user_id, product_id) VALUES
(1, 3), (1, 5), 
(2, 6), 
(3, 4), (3, 9), 
(4, 7), 
(5, 2), (5, 10);
