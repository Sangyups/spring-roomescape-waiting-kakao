DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation
(
    id          bigint not null auto_increment,
    schedule_id bigint not null,
    member_id   bigint not null,
    primary key (id)
);

DROP TABLE IF EXISTS theme;
CREATE TABLE theme
(
    id    bigint       not null auto_increment,
    name  varchar(20)  not null,
    desc  varchar(255) not null,
    price int          not null,
    primary key (id)
);

DROP TABLE IF EXISTS schedule;
CREATE TABLE schedule
(
    id       bigint not null auto_increment,
    theme_id bigint not null,
    date     date   not null,
    time     time   not null,
    primary key (id)
);

DROP TABLE IF EXISTS member;
CREATE TABLE member
(
    id       bigint       not null auto_increment,
    username varchar(20)  not null,
    password varchar(100) not null,
    name     varchar(20)  not null,
    phone    varchar(20)  not null,
    role     varchar(20)  not null,
    primary key (id),
    unique (username)
);

DROP TABLE IF EXISTS reservation_waiting;
CREATE TABLE reservation_waiting
(
    id            bigint  not null auto_increment,
    member_id     bigint  not null,
    schedule_id   bigint  not null,
    waiting_count bigint  not null,
    is_deleted    tinyint not null default 0,
    primary key (id)
);

--- Table for optimistic lock
DROP TABLE IF EXISTS waiting_counter;
CREATE TABLE waiting_counter
(
    schedule_id   bigint not null,
    waiting_count bigint not null,
    primary key (schedule_id),
    foreign key (schedule_id) references schedule (id),
);
