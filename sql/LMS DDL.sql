-- delete database if exists LMS;
drop database if exists LMS;
create database if not exists LMS default character set utf8 collate utf8_general_ci;

-- create schema if not exists book;
-- create schema if not exists book_info;
-- create schema if not exists reader;
-- create schema if not exists fine;
-- create schema if not exists book_in_need;
-- create schema if not exists borrow;
-- 
use LMS;

-- drop table if exists borrow;
-- drop table if exists book_info;
-- drop table if exists book;
-- drop table if exists fine;
-- drop table if exists book_in_need;
-- drop table if exists reader;

create table book
(
	ISBN	numeric(10,0),
    book_name	varchar(50),
    publish_house	varchar(50),
    writer	varchar(50),
    category	enum ('A','B','C','D','E','F','G','H','I','J','K','N','O','P','Q','R','S','T','U','V','X','Z'),
    primary key(ISBN)-- ,book_name,publish_house,writer)
);

create table book_info
(
	ISBN	numeric(10,0),
	-- book_name   varchar(50),
    total	numeric(2,0),
    duplicate_num	numeric(2,0),
	state	enum ('on','out','in need') default 'on',
    primary key(ISBN,duplicate_num),
    foreign key(ISBN) references book(ISBN)
		on update cascade
		on delete cascade
);

create table reader
(
	usr_num	smallint(6) auto_increment,
    usr_name	varchar(50),
    address	varchar(50),
    email	varchar(256),-- email set ('%@%.%'),
    phone	varchar(13),
    total	numeric(2,0) default 0,
    password	varchar(50) not null,
    primary key(usr_num),
    CONSTRAINT uc_PersonID UNIQUE (usr_name,phone)
);

create table book_in_need
(
	book_name	varchar(50),
    publish_house	varchar(50),
    writer	varchar(50),
    state	enum('finding','determined','notyet') default 'notyet',
    primary key(book_name,writer)
);
-- 荐购表
-- 主要是输入书名和作者名，如果可能输入出版商，可以在Java中自动补全

create table borrow
(
	usr_num	smallint(6),
    ISBN	numeric(10,0),
    -- duplicate_num	numeric(2,0),
    borrow_day	timestamp,
    borrow_times	numeric(1,0) default 1,
    primary key(usr_num,ISBN),
    foreign key(usr_num) references reader(usr_num),
    foreign key(ISBN) references book_info(ISBN)
);
-- 
-- create table statics
-- (
-- 	usr_id	numeric(10,0),
--     primary key(usr_id)
-- );
-- 
create table waiting
(
	ISBN	numeric(10,0),
    usr_num	smallint(6),
    request_time	timestamp,
    state	enum('Satisfied','notyet')default 'notyet',
    primary key(ISBN,usr_num),
    foreign key(ISBN)references	book(ISBN)
    on delete cascade
    on update cascade,
    foreign key(usr_num)references reader(usr_num)
);

delimiter //
    
create trigger borrow_delete_change_reader_total after delete on borrow
for each row
begin
	
	if (select total
		from reader
        where reader.usr_num = old.usr_num) > 0 then
		update reader
        set total = total-1
        where usr_num = old.usr_num;
-- 	else
-- 		rollback to savepoint_i;
	end if;
end//

create trigger total_change_after_insert_in_borrow after insert on borrow
for each row
begin
	
	if (select total
		from reader
        where reader.usr_num = new.usr_num) < 12 then
		update reader
        set total = total+1
        where usr_num = new.usr_num;
-- 	else
-- 		rollback to savepoint_i;
	end if;
end//

-- insert into statics values(1)//
insert into book values('1111111111','C#从入门到入土', 'peoplerepublic', 'me', 'A')//
insert into book values('1111111112','Java从入门到入土','peoplerepublic', 'me', 'A')//
insert into book_info values(1111111111,4,1,'on')//
insert into book_info values(1111111111,4,2,'out')//
insert into book_info values(1111111111,4,3,'on')//
insert into book_info values(1111111111,4,4,'on')//
insert into book_info values(1111111112,1,1,'out')//
insert into reader (usr_num,usr_name,address,email,phone,password)values(null,'jean','11','111','111','zzq123')//
insert into borrow (usr_num,ISBN)values(1,1111111111)//
insert into borrow (usr_num,ISBN)values(1,1111111112)//
