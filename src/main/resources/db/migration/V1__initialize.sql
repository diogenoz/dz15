-- flyway don't work with oracle, this scripts must execute manually
DROP TABLE task;
DROP TABLE employee;
DROP SEQUENCE s_employee;
CREATE SEQUENCE s_employee
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;
DROP SEQUENCE s_task;
CREATE SEQUENCE s_task
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE TABLE employee (id integer PRIMARY KEY, name VARCHAR2(255), age INTEGER);

INSERT INTO employee(id, name, age) VALUES
(s_employee.NEXTVAL, 'Ivanov',  24);
INSERT INTO employee(id, name, age) VALUES
(s_employee.NEXTVAL, 'Petrov',  33);
INSERT INTO employee(id, name, age) VALUES
(s_employee.NEXTVAL, 'Sidorov', 44);

CREATE TABLE task
(id INTEGER PRIMARY KEY
,name VARCHAR2(255)
,owner INTEGER REFERENCES employee (id)
,assignee INTEGER REFERENCES employee (id)
,description VARCHAR2(255)
,status VARCHAR2(255)
);