create table "user" (
                        id int unsigned not null AUTO_INCREMENT primary key,
                        account_id varchar(12) unique not null check (account_id REGEXP'[a-z0-9]{6,12}'),
    password varchar(256) not null,
    nickname varchar(24) unique not null,
    email varchar(320) not null check (email regexp '^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$'),
    written_time timestamp default current_timestamp,
    update_time timestamp default current_timestamp,
    del_req_time timestamp
);

create table post
(
    id              bigint unsigned not null AUTO_INCREMENT primary key,
    board_id        smallint    not null,
    title           varchar(50) not null,
    content         text        not null,
    written_user_id int unsigned,
    written_time    timestamp   not null default current_timestamp,
    update_time     timestamp   not null default current_timestamp,
    views           int unsigned not null default 0,
    non_mem_nick    varchar(24),
    non_mem_pw      varchar(20)
);