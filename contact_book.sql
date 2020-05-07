-- Script to create Database

create database contact_book;

-- to connect to DB: \connect contact_book;

-- Script to create users table

create table users
(
id     bigserial constraint users_pk primary key,
first_name varchar(100) not null,
last_name  varchar(100),
contact    bigint,
mail       varchar(200)
);


-- Script to create auth_user table

create table auth_user
(
id         bigserial,
userid     bigint not null unique constraint id references users,
user_name  varchar(50) not null unique,
password   text
);

alter table auth_user add constraint auth_user_pk primary key (id, userid, user_name);

-- Script to create contact and contact_list tables

create table contact (
id  bigserial constraint contact_pk primary key ,
userid bigint,
contactid bigint
);

alter table contact drop constraint contact_userid_fk;
alter table contact add constraint contact_userid_fk foreign key(userid) references users(id);
alter table contact add constraint contact_contactid_fk foreign key(contactid) references contact_list(id);


create  table contact_list(
id bigserial constraint contact_list_pk primary key,
first_name varchar(100) not null ,
last_name varchar(100) ,
number bigint ,
mail varchar(100)
);
