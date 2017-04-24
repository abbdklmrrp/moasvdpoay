CREATE TABLE Customers
(
  id         NUMBER(38),
  name       VARCHAR2(100) NOT NULL UNIQUE,
  invoice    NUMBER(20) UNIQUE,
  secret_key VARCHAR2(100) NOT NULL
);

CREATE TABLE Roles
(
  id   NUMBER(38),
  name VARCHAR2(100) NOT NULL
);

CREATE TABLE Users
(
  id          NUMBER(38),
  name        VARCHAR2(100)  NOT NULL,
  surname     VARCHAR2(100)  NOT NULL,
  email       VARCHAR2(100)  NOT NULL UNIQUE,
  phone       VARCHAR2(100)  NOT NULL UNIQUE,
  password    VARCHAR2(32)   NOT NULL,
  address     VARCHAR2(1000) NOT NULL,
  role_id     NUMBER(38)     NOT NULL,
  place_id    NUMBER(38),
  customer_id NUMBER(38),
  enable      NUMBER(2)
);

CREATE TABLE Complaints
(
  id            NUMBER(38),
  order_id      NUMBER(38) NOT NULL,
  csr_id        NUMBER(38),
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
  id              NUMBER(38),
  type_id         NUMBER(38)    NOT NULL,
  category_id     NUMBER(38),
  name            VARCHAR2(100) NOT NULL,
  duration        NUMBER(4),
  need_processing NUMBER(1),
  description     VARCHAR2(1000),
  status          NUMBER(1)     NOT NULL
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
  id                NUMBER(38),
  product_id        NUMBER(38) NOT NULL,
  user_id           NUMBER(38) NOT NULL,
  current_status_id NUMBER(38) NOT NULL
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
  product_id  NUMBER(38)   NOT NULL,
  place_id    NUMBER(38)   NOT NULL,
  discount    NUMBER(5, 2) NOT NULL,
  description VARCHAR2(1000),
  date_begin  DATE         NOT NULL,
  date_end    DATE         NOT NULL
);

CREATE TABLE Operation_status
(
  id   NUMBER(38),
  name VARCHAR2(1000) NOT NULL
);

CREATE TABLE Complaint_status
(
  id   NUMBER(38),
  name VARCHAR2(1000) NOT NULL
);

CREATE TABLE Operations_history
(
  id             NUMBER(38),
  order_id       NUMBER(38) NOT NULL,
  operation_date DATE       NOT NULL,
  status_id      NUMBER(38) NOT NULL
);

CREATE TABLE Product_categories
(
  id          NUMBER(38),
  name        VARCHAR2(100) NOT NULL,
  description VARCHAR2(1000)
);

CREATE TABLE Planned_tasks
(
  id          NUMBER(38),
  status_id   NUMBER(38) NOT NULL,
  order_id    NUMBER(38) NOT NULL,
  action_date DATE       NOT NULL
);

CREATE TABLE Complaint_history
(
  id             NUMBER(38),
  complaint_id   NUMBER(38) NOT NULL,
  operation_date DATE       NOT NULL,
  status_id      NUMBER(38) NOT NULL
);

ALTER TABLE Customers
  ADD CONSTRAINT customer_id_pk PRIMARY KEY (id);
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
ALTER TABLE Operation_status
  ADD CONSTRAINT operation_status_id_pk PRIMARY KEY (id);
ALTER TABLE Complaint_status
  ADD CONSTRAINT complaint_status_id_pk PRIMARY KEY (id);
ALTER TABLE Operations_history
  ADD CONSTRAINT operation_history_id_pk PRIMARY KEY (id);
ALTER TABLE Tariff_services
  ADD CONSTRAINT tariff_service_id_pk PRIMARY KEY (tariff_id, service_id);
ALTER TABLE Prices
  ADD CONSTRAINT price_id_pk PRIMARY KEY (product_id, place_id);
ALTER TABLE Product_categories
  ADD CONSTRAINT product_category_id_pk PRIMARY KEY (id);
ALTER TABLE Planned_tasks
  ADD CONSTRAINT planned_task_id_pk PRIMARY KEY (id);
ALTER TABLE Complaint_history
  ADD CONSTRAINT complaint_history_id_pk PRIMARY KEY (id);

ALTER TABLE Users
  ADD CONSTRAINT users_customer_id_fk FOREIGN KEY (customer_id) REFERENCES Customers (id);
ALTER TABLE Users
  ADD CONSTRAINT users_role_id_fk FOREIGN KEY (role_id) REFERENCES Roles (id);
ALTER TABLE Users
  ADD CONSTRAINT users_place_id_fk FOREIGN KEY (place_id) REFERENCES Places (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaints_order_id_fk FOREIGN KEY (order_id) REFERENCES Orders (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaints_csr_id_fk FOREIGN KEY (csr_id) REFERENCES Users (id);
ALTER TABLE Complaints
  ADD CONSTRAINT complaints_status_id_fk FOREIGN KEY (status_id) REFERENCES Complaint_status (id);
ALTER TABLE Orders
  ADD CONSTRAINT orders_product_id_fk FOREIGN KEY (product_id) REFERENCES Products (id);
ALTER TABLE Orders
  ADD CONSTRAINT orders_current_status_id_fk FOREIGN KEY (current_status_id) REFERENCES Operation_status (id);
ALTER TABLE Orders
  ADD CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id) REFERENCES Users (id);
ALTER TABLE Products
  ADD CONSTRAINT products_type_id_fk FOREIGN KEY (type_id) REFERENCES Product_types (id);
ALTER TABLE Products
  ADD CONSTRAINT products_category_id_fk FOREIGN KEY (category_id) REFERENCES Product_categories (id);
ALTER TABLE Tariff_services
  ADD CONSTRAINT tariff_services_tariff_id_fk FOREIGN KEY (tariff_id) REFERENCES Products (id);
ALTER TABLE Tariff_services
  ADD CONSTRAINT tariff_services_service_id_fk FOREIGN KEY (service_id) REFERENCES Products (id);
ALTER TABLE Operations_history
  ADD CONSTRAINT history_order_id_fk FOREIGN KEY (order_id) REFERENCES Orders (id);
ALTER TABLE Operations_history
  ADD CONSTRAINT history_status_id_fk FOREIGN KEY (status_id) REFERENCES Operation_status (id);
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
ALTER TABLE Planned_tasks
  ADD CONSTRAINT planned_task_status_id_fk FOREIGN KEY (status_id) REFERENCES Operation_status (id);
ALTER TABLE Planned_tasks
  ADD CONSTRAINT planned_task_order_id_fk FOREIGN KEY (order_id) REFERENCES Orders (id);
ALTER TABLE Complaint_history
  ADD CONSTRAINT history_complaint_id_fk FOREIGN KEY (complaint_id) REFERENCES Complaints (id);
ALTER TABLE Complaint_history
  ADD CONSTRAINT compl_history_stat_id_fk FOREIGN KEY (status_id) REFERENCES Complaint_status (id);


CREATE OR REPLACE VIEW Authorities AS
  SELECT
    Users.id       id,
    Users.email    username,
    Users.password password,
    Roles.name     role
  FROM Users
    JOIN Roles ON Roles.id = Users.role_id;

CREATE SEQUENCE customers_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE roles_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE operation_status_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE users_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE complaints_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE complaint_status_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE orders_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE places_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE operations_history_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE discounts_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE product_types_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE products_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE product_categories_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE planned_tasks_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE OR REPLACE TRIGGER customers_trg
BEFORE INSERT ON Customers
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT customers_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER role_trg
BEFORE INSERT ON Roles
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT roles_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER operation_status_trg
BEFORE INSERT ON Operation_status
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT operation_status_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER users_trg
BEFORE INSERT ON Users
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT users_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER complaints_trg
BEFORE INSERT ON Complaints
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT complaints_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER complaint_status_trg
BEFORE INSERT ON Complaint_status
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT complaint_status_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER orders_trg
BEFORE INSERT ON Orders
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT orders_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER places_trg
BEFORE INSERT ON Places
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT places_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER operations_history_trg
BEFORE INSERT ON Operations_history
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT operations_history_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER discounts_trg
BEFORE INSERT ON Discounts
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT discounts_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER product_types_trg
BEFORE INSERT ON Product_types
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT product_types_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER products_trg
BEFORE INSERT ON Products
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT products_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER product_categories_trg
BEFORE INSERT ON Product_categories
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT product_categories_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

CREATE OR REPLACE TRIGGER planned_tasks_trg
BEFORE INSERT ON Planned_tasks
FOR EACH ROW
  BEGIN
    IF :new.id IS NULL
    THEN
      SELECT planned_tasks_seq.nextval
      INTO :new.id
      FROM dual;
    END IF;
  END;
/

INSERT INTO Roles (name) VALUES ('ADMIN');
INSERT INTO Roles (name) VALUES ('PMG');
INSERT INTO Roles (name) VALUES ('CSR');
INSERT INTO Roles (name) VALUES ('USER');

-- INSERT INTO USERS VALUES (1,'admin','58bd1d8f8a93b0b6c12ab4ed747567f3',1);
-- INSERT INTO USERS VALUES (2,'mng','2d889e183be0dd25b335fdd5ec92002b',2);
-- INSERT INTO USERS VALUES (3,'csr','1d9e132d3a2fc27bc5fb819098038e6c',3);
-- INSERT INTO USERS VALUES (4,'user','2cd613d62f1988c770eecd11f6616801',4);

INSERT INTO Complaint_status (name) VALUES ('Send');
INSERT INTO Complaint_status (name) VALUES ('In processing');
INSERT INTO Complaint_status (name) VALUES ('Processed');

INSERT INTO Operation_status (name) VALUES ('Active');
INSERT INTO Operation_status (name) VALUES ('Suspended');
INSERT INTO Operation_status (name) VALUES ('Deactivated');
INSERT INTO Operation_status (name) VALUES ('In processing');

INSERT INTO Product_types (name) VALUES ('Tariff');
INSERT INTO Product_types (name) VALUES ('Service');

INSERT INTO Places (name) VALUES ('Ukraine');
INSERT INTO Places (name, parent_id) VALUES (q'[Kyivs'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Vinnyts'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Volyns'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Dnipropetrovsk Oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Donetsk Oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Zhytomyrs'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Zakarpats'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Zaporiz'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Ivano-Frankivs'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Kirovohrads'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Luhans''ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Lviv Oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Mykolaivs'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Odessa Oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Poltavs'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Rivnens'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Sums'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Ternopil's'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Kharkiv Oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Khersons'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Khmel'nyts'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Cherkas'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Chernihivs'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES (q'[Chernivets'ka oblast]', 1);
INSERT INTO Places (name, parent_id) VALUES ('Kyiv City', 2);

INSERT INTO Product_categories (name) VALUES ('Wired Internet');
INSERT INTO Product_categories (name) VALUES ('2G');
INSERT INTO Product_categories (name) VALUES ('3G');
INSERT INTO Product_categories (name) VALUES ('4G');
INSERT INTO Product_categories (name) VALUES ('Mobile communication');