DROP TABLE IF EXISTS authusergroup CASCADE ;
DROP TABLE IF EXISTS orderdetails  ;
DROP TABLE IF EXISTS toppings_dishes CASCADE;
DROP TABLE IF EXISTS ordercart_dishes CASCADE;
DROP TABLE IF EXISTS dishes CASCADE ;
DROP TABLE IF EXISTS foodtypes  ;
DROP TABLE IF EXISTS toppings CASCADE ;
DROP TABLE IF EXISTS payments  ;
DROP TABLE IF EXISTS orders  ;
DROP TABLE IF EXISTS ordercart  ;
DROP TABLE IF EXISTS customerscredentials CASCADE ;
DROP TABLE IF EXISTS customers  ;





CREATE TABLE customers (
   id serial PRIMARY KEY,
   name VARCHAR ( 50 ) NOT NULL,
   surname VARCHAR ( 50 ) NOT NULL,
   mobile VARCHAR ( 20) NOT NULL,
   dob DATE NOT NULL,
   email VARCHAR ( 50 ) NOT NULL,
   homeaddress VARCHAR ( 200 ) NOT NULL
);

CREATE TABLE customerscredentials (
	id serial PRIMARY KEY,
	customerid INT NOT NULL,
	username VARCHAR (20) UNIQUE NOT NULL,
	password VARCHAR (100) NOT NULL,
	FOREIGN KEY (customerid)
      REFERENCES customers (id)
);

CREATE TABLE authusergroup (
	id serial  PRIMARY KEY,
	customerscredentialsid INT ,
	authgroup VARCHAR (20) NOT NULL,
	FOREIGN KEY (customerscredentialsid)
      REFERENCES customerscredentials (id)
);

CREATE TABLE orders (
	id serial PRIMARY KEY,
	customerid INT,
	sum decimal NOT NULL,
	date DATE NOT NULL,
	FOREIGN KEY (customerid)
      REFERENCES customers (id)
	  );

CREATE TABLE foodtypes (
	id serial PRIMARY KEY,
	name VARCHAR ( 50 ) NOT NULL,
	price decimal NOT NULL,
	description VARCHAR (200) NOT NULL,
	image bytea
	);


CREATE TABLE toppings (
	id serial PRIMARY KEY,
	name VARCHAR ( 50 ) NOT NULL,
	price decimal NOT NULL
	);


CREATE TABLE dishes (
	id serial PRIMARY KEY,
	foodtypeid INT,
	FOREIGN KEY (foodtypeid)
      REFERENCES foodtypes (id)
	);



CREATE TABLE toppings_dishes (
	toppingid INT references toppings (id) ,
	dishid INT references dishes (id),
	CONSTRAINT toppings_dishes_pk PRIMARY KEY (dishid,toppingid));


CREATE TABLE ordercart (
		id serial PRIMARY KEY
	  );


CREATE TABLE ordercart_dishes (
	ordercartid INT references  ordercart(id) ,
	dishid INT references dishes (id),
	CONSTRAINT ordercart_dishes_pk PRIMARY KEY (ordercartid,dishid));



CREATE TABLE orderdetails (
	id serial PRIMARY KEY,
	orderid INT,
	ordercartid INT,
	deliveryname VARCHAR ( 50 ) NOT NULL,
	deliveryMOBILE VARCHAR ( 50 ) NOT NULL,
	deliveryADDRESS VARCHAR ( 200 ) NOT NULL,
	SPECIALCOMMENTS VARCHAR ( 200 ) NOT NULL,
	deliveryprice decimal NOT NULL,
	FOREIGN KEY (orderid)
      REFERENCES orders (id),
	FOREIGN KEY (ordercartid)
      REFERENCES orderCart (id)
	  );

CREATE TABLE payments (
	id serial PRIMARY KEY,
	orderid INT,
	paymentmethod VARCHAR ( 20 ) NOT NULL,
	FOREIGN KEY (orderid)
      REFERENCES orders (id)
	  );



	  
	  



INSERT INTO customers (name, surname,mobile,dob,email,homeaddress)
VALUES('BOB','DYLAN',+77011112233,'2022-06-01','BOBDYLAN@GMAIL.COM','BOBDYLANS HOME ADDEESS');


INSERT INTO customerscredentials (customerid, username,password)
VALUES
('1','BOBDYLAN','$2a$10$v2qtFXNMtty347.iYAVu1.94T.Hk1L19jFHWis4EjExx.Jh7S0fWa')

;


INSERT INTO authusergroup (customerscredentialsid,authgroup)
VALUES
(1,'ADMIN'),
(1,'CUSTOMER');



INSERT INTO foodtypes (name,price,description) VALUES
('Pizza Pepperoni',1500, 'Savoury pepperoni, homestyle sauce, and ooey-gooey, stretchy mozzarella'),
('Pizza Buffalo chicken',1850, 'BBQ Chicken, red onions, parmesan cheese on a Buffalo sauce base. '),
('Pizza Meat supreme',2500, 'Classic pepperoni, bacon crumble, salami, spicy Italian sausage, mozzarella cheese '),
('Pizza Vegetarian',1230, 'Mozzarella cheese, our signature tomato sauce, mushroom, onion, peppers, cherry tomatoes.'),
('Pizza Europa pizza',2400, 'Sun-dried tomato, goat cheese, fresh basil and mozzarella.'),
('Pizza Hawaiian pizza',1500, 'Ham and pineapple.'),
('Pizza Napoli',2500,'just default pizza4');



INSERT INTO toppings (name,price) VALUES
('cheese',100),
('pepper',150),
('jalapeno',300),
('olives',500);
