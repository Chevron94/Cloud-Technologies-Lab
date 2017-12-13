CREATE TABLE server
(
id serial primary key,
name VARCHAR(128) not null,
uri VARCHAR(256) not null
);

CREATE TABLE object
(
id serial primary key,
name VARCHAR(128) not null,
server_id integer REFERENCES server (id)
);

CREATE SEQUENCE SERVER_ID_SEQ START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE OBJECT_ID_SEQ START WITH 0 INCREMENT BY 1;