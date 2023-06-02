create schema if not exists cloudStorage;

create table if not exists cloudStorage.files
(
    id     int primary key auto_increment,
    filename   varchar(255) not null,
    size    int       not null,

    deleted     int,
    path   varchar(255) not null
);
create table if not exists cloudStorage.roles(
                                                 id int primary key auto_increment,
                                                 name varchar(255) NOT NULL,
                                                 status varchar(255) NOT NULL
                                                 );

 create table if not exists cloudStorage.user_roles(
                                                  user_id varchar(255) NOT NULL,
                                                  role_id varchar(255) NOT NULL
 );


create table if not exists cloudStorage.users(
                                                 id int primary key auto_increment,
                                                 status varchar(255) NOT NULL,
                                                 login varchar(255) NOT NULL,
                                                 password varchar(255) NOT NULL

    );

##create table if not exists cloudStorage.tokens(
  ##  token varchar(255) primary key NOT NULL
  ##  );