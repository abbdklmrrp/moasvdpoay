CREATE TABLE Categories
(
  id   NUMBER(38),
  name VARCHAR2(100) NOT NULL
);

CREATE TABLE Roles
(
  id   NUMBER(38),
  name VARCHAR2(100) NOT NULL
);

CREATE TABLE Users
(
  id        NUMBER(38),
  name      VARCHAR2(100) NOT NULL,
  surname   VARCHAR2(100) NOT NULL,
  email     VARCHAR2(100) NOT NULL,
  phone     VARCHAR2(100) NOT NULL,
  password  VARCHAR2(32)  NOT NULL,
  role_id   NUMBER(38)    NOT NULL,
  city_id   NUMBER(38),
  region_id NUMBER(38),
  enable    NUMBER(2)
);

CREATE TABLE Complaints
(
  id            NUMBER(38),
  user_id       NUMBER(38),
  category_id   NUMBER(38),
  creating_date DATE       NOT NULL,
  status_id     NUMBER(38) NOT NULL,
  description   VARCHAR2(1000)
);

CREATE TABLE Product_types
(
  id   NUMBER(38),
  name VARCHAR2(100) NOT NULL
);

CREATE TABLE Products
(
  id         NUMBER(38),
  type_id    NUMBER(38)    NOT NULL,
  base_price NUMBER(20, 4) NOT NULL,
  duration   NUMBER(4)
);

CREATE TABLE Tariff_services
(
  tariff_id  NUMBER(38),
  service_id NUMBER(38)
);

CREATE TABLE Places
(
  id        NUMBER(38),
  parent_id NUMBER(38),
  name      VARCHAR2(100) NOT NULL
);

CREATE TABLE Orders
(
  id         NUMBER(38),
  product_id NUMBER(38) NOT NULL,
  date_begin DATE       NOT NULL,
  date_end   DATE,
  status_id  NUMBER(38) NOT NULL
);

CREATE TABLE Prices
(
  product_id NUMBER(38),
  place_id   NUMBER(38),
  price      NUMBER(20, 4) NOT NULL
);

CREATE TABLE Discounts
(
  id          NUMBER(38),
  product_id  NUMBER(38)    NOT NULL,
  place_id    NUMBER(38)    NOT NULL,
  new_price   NUMBER(20, 4) NOT NULL,
  description VARCHAR2(1000),
  date_begin  DATE          NOT NULL,
  date_end    DATE          NOT NULL
);

CREATE TABLE Statuses
(
  id          NUMBER(38),
  description VARCHAR2(1000) NOT NULL
);

CREATE TABLE Logined
(
  id        NUMBER(38),
  series    VARCHAR2(100) NOT NULL,
  token     VARCHAR2(100) NOT NULL,
  last_used DATE          NOT NULL
);

CREATE TABLE Operations_history
(
  id             NUMBER(38),
  order_id       NUMBER(38) NOT NULL,
  operation_date DATE       NOT NULL,
  status_id      NUMBER(38) NOT NULL
);

ALTER TABLE Categories
  ADD CONSTRAINT category_id_pk PRIMARY KEY (id);
ALTER TABLE Roles
  ADD CONSTRAINT role_id_pk PRIMARY KEY (id);
ALTER TABLE Users
  ADD CONSTRAINT user_id_pk PRIMARY KEY (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaint_id_pk PRIMARY KEY (id);
ALTER TABLE Product_types
  ADD CONSTRAINT product_type_id_pk PRIMARY KEY (id);
ALTER TABLE Products
  ADD CONSTRAINT product_id_pk PRIMARY KEY (id);
ALTER TABLE Places
  ADD CONSTRAINT place_id_pk PRIMARY KEY (id);
ALTER TABLE Orders
  ADD CONSTRAINT order_id_pk PRIMARY KEY (id);
ALTER TABLE Discounts
  ADD CONSTRAINT discount_id_pk PRIMARY KEY (id);
ALTER TABLE Statuses
  ADD CONSTRAINT status_id_pk PRIMARY KEY (id);
ALTER TABLE Logined
  ADD CONSTRAINT logined_id_pk PRIMARY KEY (id);
ALTER TABLE Operations_history
  ADD CONSTRAINT operation_history_id_pk PRIMARY KEY (id);
ALTER TABLE Tariff_services
  ADD CONSTRAINT tariff_service_id_pk PRIMARY KEY (tariff_id, service_id);
ALTER TABLE Prices
  ADD CONSTRAINT price_id_pk PRIMARY KEY (product_id, place_id);


ALTER TABLE Users
  ADD CONSTRAINT users_role_id_fk FOREIGN KEY (role_id) REFERENCES Roles (id);
ALTER TABLE Users
  ADD CONSTRAINT users_city_id_fk FOREIGN KEY (city_id) REFERENCES Places (id);
ALTER TABLE Users
  ADD CONSTRAINT users_region_id_fk FOREIGN KEY (region_id) REFERENCES Places (id);
ALTER TABLE Logined
  ADD CONSTRAINT logined_user_id_fk FOREIGN KEY (id) REFERENCES Users (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaints_user_id_fk FOREIGN KEY (user_id) REFERENCES Users (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaints_category_id_fk FOREIGN KEY (category_id) REFERENCES Categories (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaints_status_id_fk FOREIGN KEY (status_id) REFERENCES Statuses (id);
ALTER TABLE Orders
  ADD CONSTRAINT orders_product_id_fk FOREIGN KEY (product_id) REFERENCES Products (id);
ALTER TABLE Orders
  ADD CONSTRAINT orders_status_id_fk FOREIGN KEY (status_id) REFERENCES Statuses (id);
ALTER TABLE Products
  ADD CONSTRAINT products_type_id_fk FOREIGN KEY (type_id) REFERENCES Product_types (id);
ALTER TABLE Tariff_services
  ADD CONSTRAINT tariff_services_tariff_id_fk FOREIGN KEY (tariff_id) REFERENCES Products (id);
ALTER TABLE Tariff_services
  ADD CONSTRAINT tariff_services_service_id_fk FOREIGN KEY (service_id) REFERENCES Products (id);
ALTER TABLE Operations_history
  ADD CONSTRAINT history_order_id_fk FOREIGN KEY (order_id) REFERENCES Orders (id);
ALTER TABLE Operations_history
  ADD CONSTRAINT history_status_id_fk FOREIGN KEY (status_id) REFERENCES Statuses (id);
ALTER TABLE Places
  ADD CONSTRAINT places_parent_id_fk FOREIGN KEY (parent_id) REFERENCES Places (id);
ALTER TABLE Prices
  ADD CONSTRAINT prices_price_product_id_fk FOREIGN KEY (product_id) REFERENCES Products (id);
ALTER TABLE Prices
  ADD CONSTRAINT prices_place_id_fk FOREIGN KEY (place_id) REFERENCES Places (id);
ALTER TABLE Discounts
  ADD CONSTRAINT discounts_product_id_fk FOREIGN KEY (product_id) REFERENCES Products (id);
ALTER TABLE Discounts
  ADD CONSTRAINT discounts_place_id_fk FOREIGN KEY (place_id) REFERENCES Places (id);

CREATE OR REPLACE VIEW Persistent_logins AS
  SELECT
    Users.phone       username,
    Logined.series    series,
    Logined.token     token,
    Logined.last_used last_used
  FROM Users
    JOIN Logined ON Users.id = Logined.id;

CREATE OR REPLACE VIEW AUTHORITIES AS
  SELECT
    Users.EMAIL username,
    Users.PASSWORD,
    Roles.NAME  role
  FROM Users
    JOIN Roles ON Roles.id = Users.role_id;

  INSERT INTO ROLES VALUES (1,'ADMIN');
  INSERT INTO ROLES VALUES (2,'MANAGER');
  INSERT INTO ROLES VALUES (3,'SUPPORT');
  INSERT INTO ROLES VALUES (4,'USER');

  INSERT INTO USERS VALUES (1,'admin','58bd1d8f8a93b0b6c12ab4ed747567f3',1);
  INSERT INTO USERS VALUES (2,'manager','2d889e183be0dd25b335fdd5ec92002b',2);
  INSERT INTO USERS VALUES (3,'support','1d9e132d3a2fc27bc5fb819098038e6c',3);
  INSERT INTO USERS VALUES (4,'user','2cd613d62f1988c770eecd11f6616801',4);

COMMIT;