create table person
(
    id integer not null,
    name varchar (255) not null,
    location varchar (255),
    birth_date timestamp,
    primary key (id)
);

INSERT INTO PERSON VALUES(10001, 'JORGE','WINTERTHUR', SYSDATE());
INSERT INTO PERSON VALUES(10002, 'ALICIA','WINTERTHUR', SYSDATE());
INSERT INTO PERSON VALUES(10003, 'VIDAL','WINTERTHUR', SYSDATE());
INSERT INTO PERSON VALUES(10004, 'DANA','WINTERTHUR', SYSDATE());
