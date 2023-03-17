CREATE DATABASE cheboin;
\c cheboin;
CREATE TABLE patients (id SERIAL PRIMARY KEY, name VARCHAR, nationalid VARCHAR, datetreated VARCHAR, infection VARCHAR,  tel VARCHAR, amount VARCHAR,officerid INTEGER );
CREATE TABLE officers (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE DATABASE  cheboin_test WITH TEMPLATE  cheboin;