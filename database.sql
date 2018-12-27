create database Company;

CREATE TABLE Employees(
	id int,
    f_name varchar(255) ,
    l_name varchar(255) ,
	b_date date,
    gender varchar(255),
    phone_num int,
    e_mail varchar(255),
    address varchar(255),
    department varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE Supervisor(
	s_id int,
    PRIMARY KEY (s_id),
    FOREIGN KEY (s_id) REFERENCES Employees(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Customers(
	c_id int ,
    c_f_name varchar(255) ,
    c_l_name varchar(255) ,
    c_phone_num int,
    c_e_mail varchar(255),
    c_address varchar(255),
    PRIMARY KEY(c_id)
);

CREATE TABLE Products(
	p_id int NOT NULL,
    p_name varchar(255) ,
    p_price float,
    p_quantity int,
    PRIMARY KEY(p_id)
);

CREATE TABLE Salaries(
	id int ,
    salary float,
    from_date date,
    to_date date,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES Employees(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Departments(
	d_name varchar(255) ,
    supervisor_id int ,
    location varchar(255) ,
    n_of_employees int,
    PRIMARY KEY(d_name),
    FOREIGN KEY(supervisor_id) REFERENCES Supervisor(s_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Department_work(
	d_name varchar(255) ,
    d_work varchar(255) ,
    PRIMARY KEY (d_work),
    FOREIGN KEY (d_name) REFERENCES Departments(d_name) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Departments(d_name, supervisor_id, location, n_of_employees)
VALUES ('Economics', 1, 'somewere', 10);


INSERT INTO Employees(id, f_name, l_name, b_date, gender, phone_num, e_mail, address, department)
VALUES(1, 'Kostas', 'Stavridis', '2015-12-17', 'M', 6988, 'vss.stavridis@uth.gr', 'tampara 1', 'Economics');

INSERT INTO Supervisor(s_id)
VALUES (1);

INSERT INTO Department_work(d_name, d_work)
VALUES ('Economics', 'Economics and stuff');

INSERT INTO Salaries(id, salary, from_date, to_date)
VALUES (1, 3000, '2018-12-27', '2019-12-27');

INSERT INTO Customers(c_id, c_f_name, c_l_name, c_phone_num, c_e_mail, c_address)
VALUES (24, 'John', 'Mcain', 690012334, 'mcain.22@gmail.com', 'kalaa 22');

INSERT INTO Products(p_id, p_name, p_price, p_quantity)
VALUES (44, 'potatoes', 2, 13000);

delete from employees where id=1;

alter table Customers
Modify COLUMN c_phone_num bigint;

UPDATE Employees
SET b_date = '2018-12-27'
WHERE id=1;

DELETE FROM Employees WHERE id=1;

select * FROM employees;
select * FROM supervisor;
select * FROM departments;
select * FROM salaries;
