create schema if not exists cloudStorage;

create table if not exists cloudStorage.files
(
    file_id     int primary key auto_increment,
    file_name   char(200) not null,
    size    long       not null,
    content     longblob  not null,
    deleted     int
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