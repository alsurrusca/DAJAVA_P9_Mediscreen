DROP database IF EXISTS mediscreen;
create database mediscreen;
use mediscreen;

CREATE TABLE IF NOT EXISTS patient (
  id tinyint NOT NULL AUTO_INCREMENT,
  last_name VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  birth_date DATE NOT NULL,
  gender VARCHAR(1) NOT NULL,
  address VARCHAR(255) NOT NULL,
  phone VARCHAR(12) NOT NULL
);

INSERT INTO patient (first_name, last_name, birth_date,gender,address,phone)
values ('Bob', 'Morran', '2022-05-21', 'M', '6 Rue du lac', '060904');