--CREATE DATABASE bank
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	username VARCHAR(30) PRIMARY KEY,
	user_pass VARCHAR(50) NOT NULL,
	user_type VARCHAR(20) NOT NULL
);

CREATE TABLE accounts (
	account_number VARCHAR(10) PRIMARY KEY,
	balance NUMERIC(21,2) NOT NULL,
	active BOOLEAN NOT NULL DEFAULT FALSE,
	account_type VARCHAR(20) NOT NULL DEFAULT 'Client',
	account_user VARCHAR(30) REFERENCES users(username)
);

INSERT INTO users (username, user_pass, user_type)
	VALUES('kevin', 'pass', 'Employee'),
	('tim', 'pass', 'Administrator');

