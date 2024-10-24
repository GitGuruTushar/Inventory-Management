-- Create core tables
CREATE TABLE Category (
    categoryId SERIAL PRIMARY KEY,
    categoryName VARCHAR(255) NOT NULL
);

CREATE TABLE Supplier (
    supplierId SERIAL PRIMARY KEY,
    supplierName VARCHAR(255) NOT NULL,
    suplierAddress TEXT,
    contactInfo TEXT
);

CREATE TABLE Product (
    productID SERIAL PRIMARY KEY,
    productName VARCHAR(255) NOT NULL,
    productDescription TEXT,
    categoryId INT REFERENCES Category(categoryId),
    supplierId INT REFERENCES Supplier(supplierId),
    unitPrice DECIMAL(10,2),
    reorderLevel INT
);

CREATE TABLE Stock (
    stockID SERIAL PRIMARY KEY,
    productID INT REFERENCES Product(productID),
    quantityInStock INT NOT NULL DEFAULT 0,
    lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Dish (
    dishId SERIAL PRIMARY KEY,
    dishName VARCHAR(255) NOT NULL,
    dishDescription TEXT,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE Recipe (
    recipeID SERIAL PRIMARY KEY,
    dishId INT REFERENCES Dish(dishId),
    productID INT REFERENCES Product(productID),
    quantityRequired DECIMAL(10,3) NOT NULL,
    UNIQUE(dishId, productID)
);

-- Create indexes for better query performance
CREATE INDEX idx_product_category ON Product(categoryId);
CREATE INDEX idx_product_supplier ON Product(supplierId);
CREATE INDEX idx_stock_product ON Stock(productID);
CREATE INDEX idx_recipe_dish ON Recipe(dishId);
CREATE INDEX idx_recipe_product ON Recipe(productID);

-- Insert sample data
-- Categories
INSERT INTO Category (categoryName) VALUES
('Meat and Poultry'),
('Dairy'),
('Vegetables'),
('Grains and Pasta'),
('Spices and Seasonings'),
('Seafood');

-- Suppliers
INSERT INTO Supplier (supplierName, supplierAddress, contactInfo) VALUES
('Fresh Foods Inc', '123 Supplier Street, Food City', 'contact@freshfoods.com | 555-0123'),
('Quality Meats', '456 Butcher Avenue, Meat Town', 'orders@qualitymeats.com | 555-0124'),
('Ocean Fresh Seafood', '789 Harbor Road, Port City', 'sales@oceanfresh.com | 555-0125'),
('Organic Produce Co', '321 Farm Lane, Green Valley', 'support@organicproduce.com | 555-0126'),
('Spice Traders Ltd', '654 Flavor Street, Spice Market', 'orders@spicetraders.com | 555-0127');

-- Products
INSERT INTO Product (productName, productDescription, categoryId, supplierId, unitPrice, reorderLevel, recipeId) VALUES
('Chicken Breast', 'Fresh boneless chicken breast', 1, 2, 5.99, 50, 1),
('Basmati Rice', 'Premium aged basmati rice', 4, 1, 2.50, 100, NULL),
('Fresh Salmon', 'Atlantic salmon fillet', 6, 3, 12.99, 30, 6),
('Heavy Cream', 'Full-fat heavy cream', 2, 1, 3.99, 40, 3),
('Black Pepper', 'Freshly ground black pepper', 5, 5, 4.99, 25, 8),
('Garlic', 'Fresh garlic bulbs', 3, 4, 1.99, 45, 5),
('Pasta', 'Italian spaghetti', 4, 1, 1.99, 75, 2),
('Tomatoes', 'Fresh roma tomatoes', 3, 4, 2.99, 60, 11),
('Parmesan Cheese', 'Aged Parmigiano-Reggiano', 2, 1, 8.99, 30, 4),
('Olive Oil', 'Extra virgin olive oil', 5, 5, 6.99, 40, 7);

-- Stock
INSERT INTO Stock (productID, quantityInStock) VALUES
(1, 75),  -- Chicken Breast
(2, 150), -- Basmati Rice
(3, 45),  -- Fresh Salmon
(4, 60),  -- Heavy Cream
(5, 35),  -- Black Pepper
(6, 55),  -- Garlic
(7, 100), -- Pasta
(8, 80),  -- Tomatoes
(9, 40),  -- Parmesan Cheese
(10, 50); -- Olive Oil

-- Dishes
INSERT INTO Dish (dishName, dishDescription, price) VALUES
('Chicken Alfredo', 'Creamy pasta with grilled chicken breast', 16.99),
('Grilled Salmon', 'Fresh salmon with herbs and lemon', 22.99),
('Spaghetti Pomodoro', 'Classic tomato pasta with fresh basil', 14.99),
('Garlic Bread', 'Toasted bread with garlic and herbs', 5.99);

-- Recipes (ingredients needed for each dish)
INSERT INTO Recipe (dishId, productID, quantityRequired) VALUES
-- Chicken Alfredo
(1, 1, 0.250),  -- 250g Chicken Breast
(1, 7, 0.200),  -- 200g Pasta
(1, 4, 0.200),  -- 200ml Heavy Cream
(1, 9, 0.050),  -- 50g Parmesan Cheese
(1, 6, 0.020),  -- 20g Garlic
-- Grilled Salmon
(2, 3, 0.200),  -- 200g Fresh Salmon
(2, 10, 0.030), -- 30ml Olive Oil
(2, 5, 0.005),  -- 5g Black Pepper
-- Spaghetti Pomodoro
(3, 7, 0.200),  -- 200g Pasta
(3, 8, 0.300),  -- 300g Tomatoes
(3, 6, 0.020),  -- 20g Garlic
(3, 10, 0.030), -- 30ml Olive Oil
-- Garlic Bread
(4, 6, 0.030),  -- 30g Garlic
(4, 10, 0.020); -- 20ml Olive Oil

-- Create views for common queries
CREATE VIEW DishIngredients AS
SELECT 
    d.dishName,
    p.productName as Ingredient,
    r.quantityRequired,
    p.unitPrice,
    (r.quantityRequired * p.unitPrice) as IngredientCost
FROM Dish d
JOIN Recipe r ON d.dishId = r.dishId
JOIN Product p ON r.productID = p.productID;

CREATE VIEW LowStockProducts AS
SELECT 
    p.productName,
    s.QuantityInStock,
    p.reorderLevel,
    sup.supplierName,
    sup.ContactInfo
FROM Product p
JOIN Stock s ON p.productID = s.productID
JOIN Supplier sup ON p.supplierId = sup.supplierId
WHERE s.QuantityInStock <= p.reorderLevel;

CREATE VIEW ProductCategories AS
SELECT 
    p.productName,
    c.categoryName,
    s.supplierName
FROM Product p
JOIN Category c ON p.categoryId = c.categoryId
JOIN Supplier s ON p.supplierId = s.supplierId;