CREATE TABLE IF NOT EXISTS order_item_table (
    id SERIAL PRIMARY KEY,
    coffee_name TEXT,
    coffee_price DOUBLE PRECISION,
    quantity INT,
    order_id INT,
    total_amount DOUBLE PRECISION
);
