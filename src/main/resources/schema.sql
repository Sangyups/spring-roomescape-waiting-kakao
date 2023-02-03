DROP TABLE IF EXISTS waiting_counter;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS theme;

CREATE TABLE reservation
(
    id            bigint       not null auto_increment,
    schedule_id   bigint       not null,
    member_id     bigint       not null,
    status        varchar(255) not null,
    waiting_count bigint       not null,
    primary key (id),
    key           schedule_idx (schedule_id),
    key           member_idx (member_id),
    constraint schedule_count_uk UNIQUE (schedule_id, waiting_count)
);


CREATE TABLE theme
(
    id          bigint       not null auto_increment,
    name        varchar(20)  not null,
    description varchar(255) not null,
    price       int          not null,
    primary key (id)
);


CREATE TABLE schedule
(
    id       bigint not null auto_increment,
    theme_id bigint not null,
    date     date   not null,
    time     time   not null,
    primary key (id)
);

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

CREATE TABLE waiting_counter
(
    schedule_id   bigint not null,
    waiting_count bigint not null,
    primary key (schedule_id),
    foreign key (schedule_id) references schedule (id)
);
