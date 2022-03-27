/*
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
*/
INSERT INTO course(id, name, version, create_date, update_date) VALUES(10000, 'German', 1, SYSDATE(), SYSDATE());
INSERT INTO course(id, name, version, create_date, update_date) VALUES(10001, 'English', 1, SYSDATE(), SYSDATE());
INSERT INTO course(id, name, version, create_date, update_date) VALUES(10002, 'Spanish', 1, SYSDATE(), SYSDATE());
INSERT INTO course(id, name, version, create_date, update_date) VALUES(10003, 'French', 1, SYSDATE(), SYSDATE());