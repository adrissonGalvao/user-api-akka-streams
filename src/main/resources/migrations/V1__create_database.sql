create schema user_db;


CREATE TABLE  user_db.tb_user (
 id serial PRIMARY KEY,
 name VARCHAR (50) NOT NULL,
 username VARCHAR (50) UNIQUE NOT NULL,
 birth_date TIMESTAMP NOT NULL
);