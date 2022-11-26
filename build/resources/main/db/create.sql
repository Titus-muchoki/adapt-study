CREATE DATABASE viralloads;
\c viralloads;
CREATE TABLE infections (id SERIAL PRIMARY KEY, description VARCHAR, name VARCHAR, completed BOOLEAN, categoryid INTEGER);
CREATE TABLE categories (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE DATABASE  viralloads_test WITH TEMPLATE  viralloads;