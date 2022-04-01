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
INSERT INTO course(id, name, version, create_date, update_date, deleted) VALUES(10000, 'German', 1, SYSDATE(), SYSDATE(), false);
INSERT INTO course(id, name, version, create_date, update_date, deleted) VALUES(10001, 'English', 1, SYSDATE(), SYSDATE(), false);
INSERT INTO course(id, name, version, create_date, update_date, deleted) VALUES(10002, 'Spanish', 1, SYSDATE(), SYSDATE(), false);
INSERT INTO course(id, name, version, create_date, update_date, deleted) VALUES(10003, 'French', 1, SYSDATE(), SYSDATE(), false);

INSERT INTO passport(id, number, expiry_date, version, create_date, update_date) VALUES(30000, 'J1002', '2028-01-02', 1, SYSDATE(), SYSDATE());
INSERT INTO passport(id, number, expiry_date, version, create_date, update_date) VALUES(30001, 'V1068', '2025-06-05', 1, SYSDATE(), SYSDATE());
INSERT INTO passport(id, number, expiry_date, version, create_date, update_date) VALUES(30002, 'A1048', '2023-04-15', 1, SYSDATE(), SYSDATE());

INSERT INTO student(id, name, version, create_date, update_date, passport_id) VALUES(20000, 'Jorge', 1, SYSDATE(), SYSDATE(), 30000);
INSERT INTO student(id, name, version, create_date, update_date, passport_id) VALUES(20001, 'Vidal', 1, SYSDATE(), SYSDATE(), 30001);
INSERT INTO student(id, name, version, create_date, update_date, passport_id) VALUES(20002, 'Alicia', 1, SYSDATE(), SYSDATE(), 30002);

INSERT INTO review(id, rating, description, version, create_date, update_date, course_id) VALUES(40000, 'FOUR', 'not bad', 1, SYSDATE(), SYSDATE(), 10000);
INSERT INTO review(id, rating, description, version, create_date, update_date, course_id) VALUES(40001, 'THREE', 'can be better', 1, SYSDATE(), SYSDATE(), 10000);
INSERT INTO review(id, rating, description, version, create_date, update_date, course_id) VALUES(40002, 'FIVE', 'awesome', 1, SYSDATE(), SYSDATE(), 10003);
INSERT INTO review(id, rating, description, version, create_date, update_date, course_id) VALUES(40003, 'ONE', '', 1, SYSDATE(), SYSDATE(), 10001);

INSERT INTO student_course(student_id, course_id) VALUES(20000, 10000);
INSERT INTO student_course(student_id, course_id) VALUES(20000, 10001);
INSERT INTO student_course(student_id, course_id) VALUES(20001, 10000);
INSERT INTO student_course(student_id, course_id) VALUES(20002, 10002);
INSERT INTO student_course(student_id, course_id) VALUES(20002, 10002);