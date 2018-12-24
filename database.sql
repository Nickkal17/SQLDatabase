create database Company;

CREATE TABLE Employees(
	id int NOT NULL,
    f_name varchar(255) NOT NULL,
    l_name varchar(255) NOT NULL,
	b_date varchar(255),
    gender varchar(255),
    phone_num int,
    e_mail varchar(255),
    address varchar(255),
    department varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE Customers(
	c_id int NOT NULL,
    c_f_name varchar(255) NOT NULL,
    c_l_name varchar(255) NOT NULL,
    c_phone_num int,
    c_e_mail varchar(255),
    c_address varchar(255),
    PRIMARY KEY(c_id)
);

CREATE TABLE Products(
	p_id int NOT NULL,
    p_name varchar(255) NOT NULL,
    p_price float,
    p_quantity int,
    PRIMARY KEY(p_id)
);

CREATE TABLE Salaries(
	id int NOT NULL,
    salary float,
    from_date date,
    to_date date,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES Employees(id) ON DELETE CASCADE
);

CREATE TABLE Departments(
	d_name varchar(255) NOT NULL,
    supervisor_id int NOT NULL,
    location varchar(255) NOT NULL,
    n_of_employees int,
    PRIMARY KEY(d_name),
    FOREIGN KEY(supervisor_id) REFERENCES Employees(id) ON DELETE CASCADE
);

CREATE TABLE Department_work(
	d_name varchar(255) NOT NULL,
    d_work varchar(255) NOT NULL,
    PRIMARY KEY (d_work),
    FOREIGN KEY (d_name) REFERENCES Departments(d_name) ON DELETE CASCADE
);

ALTER TABLE Employees
ADD FOREIGN KEY(department) REFERENCES Departments(d_name);
