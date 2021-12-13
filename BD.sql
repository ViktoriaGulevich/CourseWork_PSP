drop
database if exists course_work;

create
database course_work;

use
course_work;

create table user
(
    id       int primary key auto_increment,
    username varchar(100) unique,
    password varchar(100)
);

create table role
(
    id   int primary key auto_increment,
    name varchar(50)
);

create table user_roles
(
    user_id int,
    role_id int,

    constraint fk_userId foreign key (user_id) references user (id),
    constraint fk_roleId foreign key (role_id) references role (id),
    constraint pk_user_roles primary key (user_id, role_id)
);
create table flight
(
    id             int primary key auto_increment,
    from_where     varchar(100),
    to_where       varchar(100),
    departure_date datetime,
    arrival_date   datetime,
    count_of_seats int,
    cost           double
);

create table ticket
(
    id             int primary key auto_increment,
    flight_id      int,
    number_of_seat int,
    cost           double,
    constraint fk_ticket_flight foreign key (flight_id) references flight (id)
);

create table status
(
    id   int primary key auto_increment,
    name varchar(100)
);

create table ticket_statuses
(
    ticket_id int,
    status_id int,

    constraint fk_ticket_status_ticket foreign key (ticket_id) references ticket (id),
    constraint fk_ticket_status_status foreign key (status_id) references status (id),
    constraint pk_ticket_status primary key (ticket_id, status_id)
);

create table user_tickets
(
    ticket_id int,
    user_id   int,

    constraint fk_ticket_user_ticket foreign key (ticket_id) references ticket (id),
    constraint fk_ticket_user_user foreign key (user_id) references user (id),
    constraint pk_ticket_user primary key (ticket_id, user_id)
);

insert into user(username, password)
values ('user', 'user'),
       ('admin', 'admin');

insert into role(name)
values ('USER'),
       ('ADMIN');

insert into user_roles(user_id, role_id)
values (1, 1),
       (2, 2);

insert into flight(from_where, to_where, departure_date, arrival_date, count_of_seats, cost)
values ('Минск', 'Москва', '2021-09-05 23:59:45', '2021-09-05 03:30:56', 56, 100),
       ('Минск', 'Москва', '2021-05-09 13:59:45', '2021-05-09 15:10:46', 56, 50),
       ('Минск', 'Москва', '2021-07-06 20:49:45', '2021-07-07 01:08:36', 56, 100),
       ('Минск', 'Вильнюс', '2022-03-10 20:34:45', '2022-03-10 22:50:56', 40, 150),
       ('Минск', 'Вильнюс', '2022-04-15 20:34:45', '2022-04-16 01:13:34', 30, 100),
       ('Минск', 'Вильнюс', '2022-04-10 12:24:45', '2022-04-10 17:50:56', 40, 160),
       ('Минск', 'Вильнюс', '2022-05-30 10:56:25', '2022-05-30 14:05:13', 40, 150),
       ('Краснодар', 'Санкт-Петербург', '2022-05-12 17:23:34', '2022-05-12 21:10:26', 60, 145),
       ('Краснодар', 'Санкт-Петербург', '2022-06-12 13:23:34', '2022-06-12 16:20:26', 60, 135),
       ('Гомель', 'Львов', '2021-12-18 14:20:13', '2021-12-18 15:00:26', 30, 120),
	   ('Гомель', 'Львов', '2021-12-20 14:20:13', '2021-12-20 15:00:26', 30, 110),
	   ('Гомель', 'Львов', '2021-12-28 10:20:13', '2021-12-28 11:40:26', 30, 100);

insert into ticket(flight_id, number_of_seat, cost)
values (1, 6, 100),
       (2, 36, 100),
       (3, 55, 100),
       (4, 12, 100);

insert into status(name)
values ('куплен'),
       ('забронирован'),
       ('свободен');

insert into ticket_statuses(ticket_id, status_id)
values (1, 1),
       (2, 3),
       (3, 2),
       (4, 1);

insert into user_tickets(ticket_id, user_id)
values (1, 1),
       (3, 1),
       (4, 1);
