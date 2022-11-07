drop database if exists trakr;

create database trakr;
use trakr;

create table users(
    username varchar(32) not null,
    password varchar(128) not null,
    email varchar(128),
    genre varchar(32),
    phone varchar(32),

    primary key(username)
);

create table profile(
    username varchar(32) not null,
    image blob,
    content_type varchar(128),
    
    primary key(username),
    constraint fk_profile_username foreign key(username) references users(username)
);

create table watchlists(
    username varchar(32) not null,
    id int not null,
    date_added date not null,
    rating int,

    primary key(username, id),
    constraint fk_username foreign key(username) references users(username)
);