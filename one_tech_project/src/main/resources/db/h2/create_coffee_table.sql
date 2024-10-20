CREATE TABLE IF NOT EXISTS coffee (
    id serial primary key,
    name text NOT NULL UNIQUE ,
    price double
);
