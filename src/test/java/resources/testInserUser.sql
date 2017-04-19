
INSERT INTO ROLES (id, NAME) VALUES
  (1, 'admin');

INSERT INTO ROLES (id, NAME) VALUES
  (2, 'manager');

INSERT INTO ROLES (id, NAME) VALUES
  (3, 'support');

INSERT INTO ROLES (id, NAME) VALUES
  (4, 'user');

INSERT INTO USERS (ID, NAME, PASSWORD, surname, email, phone, role_id) VALUES
  (1, 'admin', '58bd1d8f8a93b0b6c12ab4ed747567f3', 'surname', 'email', '2345', 1);

INSERT INTO USERS (ID, NAME, PASSWORD, surname, email, phone, role_id) VALUES
  (2, 'manager', '2d889e183be0dd25b335fdd5ec92002b', 'surname', 'email', '2327545', 2);

INSERT INTO USERS (ID, NAME, PASSWORD, surname, email, phone, role_id) VALUES
  (3, 'support', '1d9e132d3a2fc27bc5fb819098038e6c', 'surname', 'email', '8467', 3);

INSERT INTO USERS (ID, NAME, PASSWORD, surname, email, phone, role_id) VALUES
  (4, 'user', '58bd1d8f8a93b0b6c12ab4ed747567f3', 'surname', 'email', '23451', 4);

