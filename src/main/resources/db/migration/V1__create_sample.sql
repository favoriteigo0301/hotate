create table users
(
    id         bigint primary key not null auto_increment,
    name       varchar(40)        not null,
    password   varchar(20)        not null,
    created_at timestamp,
    updated_at timestamp
);

create table sample_memo
(
    id        bigint primary key not null auto_increment,
    user_id   bigint not null,
    category_ids varchar(100),
    subject   varchar(40)        not null,
    memo      text               not null,
    image     mediumblob,
    image_name varchar(40),
    created_at timestamp,
    updated_at timestamp,
    foreign key (user_id) references users (id)
);

create table sample_category
(
    id bigint primary key not null  auto_increment,
    name varchar(40) not null,
    total mediumint,
    created_at timestamp,
    updated_at timestamp
);

insert into users(name,password,created_at,updated_at) values ('テスト','0000',now(),now());
