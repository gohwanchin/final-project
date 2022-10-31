drop database if exists trakr;

create database trakr;
use trakr;

create table users(
    username varchar(32) not null,
    password varchar(128) not null,
    profile blob,

    primary key(username)
);

create table watchlists(
    username varchar(128) not null,
    id int not null,
    date_added date not null,
    rating int,

    primary key(username, id),
    constraint fk_email foreign key(username) references users(username)
);