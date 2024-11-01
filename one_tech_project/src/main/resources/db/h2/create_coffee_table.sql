CREATE TABLE IF NOT EXISTS coffee_table (
    id serial primary key,
    name text NOT NULL UNIQUE ,
    price double
);
