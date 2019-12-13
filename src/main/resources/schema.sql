DROP TABLE IF EXISTS Sale;
DROP TABLE IF EXISTS capsule_corporation;
DROP TABLE IF EXISTS olivanders_wand_shop;
DROP TABLE IF EXISTS acme_corporation;
create table Sale 
(
   id int(5) NOT NULL AUTO_INCREMENT,
   company_name varchar(50) NOT NULL,
   order_number int(10) NOT null,
   order_date varchar(10) NOT null,
   product_id int(10) NOT null,
   quantity int(10) NOT NULL ,
   sales_price varchar(20) NOT NULL,
   currency varchar(20) NOT NULL,
   PRIMARY KEY (id)
);

create table capsule_corporation
(
 id int(5) NOT NULL AUTO_INCREMENT,
 product varchar(50) NOT NULL,
 assembly_cost varchar(20) NOT NULL,
 currency varchar(20) NOT NULL,
 PRIMARY KEY (id)
);

create table olivanders_wand_shop
(
 id int(5) NOT NULL AUTO_INCREMENT,
 name varchar(50) NOT NULL,
 build_cost varchar(20) NOT NULL,
 currency varchar(20) NOT NULL,
 PRIMARY KEY (id)
);

create table acme_corporation
(
 id int(5) NOT NULL AUTO_INCREMENT,
 description varchar(50) NOT NULL,
 purchase_price varchar(20) NOT NULL,
 currency varchar(20) NOT NULL,
  PRIMARY KEY (id)
);

