create database leave_management

create table employee(emp_id int not null primary key,emp_name varchar(50),department varchar(25),designation varchar(25),contact_no int,doj date,no_of_leaves int);

insert into employee values(1001,'Neha','IT','AP',1234567890,'2011-07-01',15);
insert into employee values(1002,'Allen','IT','Assoc P',1234567890,'2012-07-01',15);
insert into employee values(1003,'Pooja','Admin','Manager',1234567890,'2012-07-01',15);