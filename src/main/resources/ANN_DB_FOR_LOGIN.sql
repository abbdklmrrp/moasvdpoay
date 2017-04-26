DROP TABLE USERS;
DROP TABLE ROLES;
CREATE TABLE Roles
(
  id   NUMBER(38),
  name VARCHAR2(100) NOT NULL
);

CREATE TABLE Users
(
  id       NUMBER(38),
  email    VARCHAR2(100) NOT NULL,
  password VARCHAR2(32)  NOT NULL,
  role_id  NUMBER(38)    NOT NULL
);

ALTER TABLE Roles
  ADD CONSTRAINT role_id_pk PRIMARY KEY (id);
ALTER TABLE Users
  ADD CONSTRAINT user_id_pk PRIMARY KEY (id);
ALTER TABLE Users
  ADD CONSTRAINT users_role_id_fk FOREIGN KEY (role_id) REFERENCES Roles (id);

INSERT INTO ROLES VALUES (1, 'ADMIN');
INSERT INTO ROLES VALUES (2, 'PMG');
INSERT INTO ROLES VALUES (3, 'CSR');
INSERT INTO ROLES VALUES (4, 'USER');

INSERT INTO USERS VALUES (1, 'admin', '58bd1d8f8a93b0b6c12ab4ed747567f3', 1);
INSERT INTO USERS VALUES (2, 'pmg', 'e51a8bcfaf7e3d91babe728701254f25', 2);
INSERT INTO USERS VALUES (3, 'csr', 'a30cc4d3a8638d9592ef8b1274dff6ff', 3);
INSERT INTO USERS VALUES (4, 'user', '2cd613d62f1988c770eecd11f6616801', 4);

CREATE OR REPLACE VIEW AUTHORITIES AS
  SELECT
    Users.EMAIL username,
    Users.PASSWORD,
    Roles.NAME  role
  FROM Users
    JOIN Roles ON Roles.id = Users.role_id;
  
  
  